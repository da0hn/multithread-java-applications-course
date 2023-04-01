package com.da0hn.multithreading.basics.product.service.utils;

public final class LoggerUtil {

  public static void log(final String message) {
    System.out.println("[" + Thread.currentThread().getName() + "] - " + message);
  }

}
