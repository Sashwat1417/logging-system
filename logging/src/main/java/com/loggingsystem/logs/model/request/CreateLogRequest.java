package com.loggingsystem.logs.model.request;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;

public class CreateLogRequest {
  private Instant timestamp;

  @NotBlank
  private String level;

  @NotBlank
  private String message;

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

