package com.da0hn.multithreading.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggerUtil {

  public static void log(final Object message) {
    final var timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSSS");
    final var now = LocalDateTime.now();
    final var threadName = Thread.currentThread().getName();
    System.out.printf("%s: [%s] - %s%n", timeFormatter.format(now), threadName, message);
  }

}
