package com.loggingsystem.logs.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.loggingsystem.logs.constant.LogLevel;
import com.loggingsystem.logs.model.mongo.LogEntry;

@Service
public class LogQueryService {
  private final MongoTemplate mongoTemplate;

  public LogQueryService(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @SuppressWarnings("null")
  public Page<LogEntry> query(LogLevel level, Instant from, Instant to, String q, int page, int size) {
    List<Criteria> criteria = new ArrayList<>();

    if (level != null) {
      criteria.add(Criteria.where("level").is(level));
    }

    if (from != null || to != null) {
      Criteria time = Criteria.where("timestamp");
      if (from != null) time = time.gte(from);
      if (to != null) time = time.lte(to);
      criteria.add(time);
    }

    if (q != null && !q.isBlank()) {
      criteria.add(Criteria.where("message").regex(q, "i"));
    }

    Query query = new Query();
    if (!criteria.isEmpty()) {
      @SuppressWarnings("null")
      Criteria[] criteriaArray = criteria.toArray(new Criteria[0]);
      query.addCriteria(new Criteria().andOperator(criteriaArray));
    }

    Sort sort = Sort.by(Sort.Order.desc("timestamp"), Sort.Order.desc("_id"));
    Pageable pageable = PageRequest.of(page, size, sort);

    long total = mongoTemplate.count(query, LogEntry.class);

    query.with(pageable);
    List<LogEntry> items = mongoTemplate.find(query, LogEntry.class);

    return new PageImpl<>(items, pageable, total);
  }
}

