package com.loggingsystem.logs.model.mongo;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.loggingsystem.logs.constant.LogLevel;
@Document("logs")
@CompoundIndex(name = "level_timestamp_desc", def = "{'level': 1, 'timestamp': -1}")
public class LogEntry {
  @Id
  private String id;

  @Indexed(name = "timestamp_desc")
  private Instant timestamp;

  private LogLevel level;

  private String message;

  public LogEntry() {}

  public LogEntry(Instant timestamp, LogLevel level, String message) {
    this.timestamp = timestamp;
    this.level = level;
    this.message = message;
  }

  public String getId() {
    return id;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public LogLevel getLevel() {
    return level;
  }

  public void setLevel(LogLevel level) {
    this.level = level;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

