package com.da0hn.multithreading.basics.product.service.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggerUtil {

  public static void log(final String message) {
    System.out.println("[" + Thread.currentThread().getName() + "] - " + message);
  }

}
