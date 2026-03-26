package com.loggingsystem.logs.model.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<LogEntry, String> {}

