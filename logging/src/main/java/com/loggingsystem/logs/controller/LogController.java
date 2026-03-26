package com.loggingsystem.logs.controller;

import java.time.Instant;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.loggingsystem.logs.constant.LogLevel;
import com.loggingsystem.logs.model.mongo.LogEntry;
import com.loggingsystem.logs.model.mongo.LogRepository;
import com.loggingsystem.logs.model.request.CreateLogRequest;
import com.loggingsystem.logs.service.LogQueryService;

@RestController
@RequestMapping("/logs")
public class LogController {
  private final LogRepository logRepository;
  private final LogQueryService logQueryService;

  public LogController(LogRepository logRepository, LogQueryService logQueryService) {
    this.logRepository = logRepository;
    this.logQueryService = logQueryService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public LogEntry addLog(@Valid @RequestBody CreateLogRequest req) {
    LogLevel level = LogLevel.fromString(req.getLevel());
    if (level == null) {
      throw new IllegalArgumentException("level is required");
    }

    Instant ts = (req.getTimestamp() != null) ? req.getTimestamp() : Instant.now();
    LogEntry entry = new LogEntry(ts, level, req.getMessage());
    return logRepository.save(entry);
  }

  @GetMapping
  public Page<LogEntry> queryLogs(
      @RequestParam(required = false) String level,
      @RequestParam(required = false) Instant from,
      @RequestParam(required = false) Instant to,
      @RequestParam(required = false) String q,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "50") int size
  ) {
    LogLevel parsedLevel = null;
    if (level != null && !level.isBlank()) {
      parsedLevel = LogLevel.fromString(level);
      if (parsedLevel == null) {
        throw new IllegalArgumentException("invalid level: " + level);
      }
    }

    int safePage = Math.max(page, 0);
    int safeSize = Math.min(Math.max(size, 1), 500);

    return logQueryService.query(parsedLevel, from, to, q, safePage, safeSize);
  }
}

