package com.loggingsystem.logs.constant;

import java.util.Locale;

public enum LogLevel {
  DEBUG,
  INFO,
  WARN,
  ERROR;

  public static LogLevel fromString(String raw) {
    if (raw == null) return null;
    String normalized = raw.trim().toUpperCase(Locale.ROOT);
    if (normalized.isEmpty()) return null;
    return LogLevel.valueOf(normalized);
  }
}

