package com.da0hn.multithreading.completable.future.sandbox;

import com.da0hn.multithreading.commons.utils.LoggerUtil;

import java.util.concurrent.CompletableFuture;

import static com.da0hn.multithreading.commons.utils.CommonUtil.delay;

public class HelloWorldService {
  public String helloWorld() {
    delay(1000);
    LoggerUtil.log("inside Hello World delayed method");
    return "Hello World";
  }

  public String hello() {
    delay(1000);
    LoggerUtil.log("inside Hello delayed method");
    return "Hello";
  }

  public String world() {
    delay(1000);
    LoggerUtil.log("inside World delayed method");
    return "World!";
  }

  public CompletableFuture<String> worldFuture(final String input) {
    return CompletableFuture.supplyAsync(() -> {
      delay(1000);
      return input + " world!";
    });
  }

}
