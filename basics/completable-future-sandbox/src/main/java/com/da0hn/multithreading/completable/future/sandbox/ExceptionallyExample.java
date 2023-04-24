package com.da0hn.multithreading.completable.future.sandbox;

import com.da0hn.multithreading.commons.utils.CommonUtil;
import com.da0hn.multithreading.commons.utils.LoggerUtil;

import java.util.concurrent.CompletableFuture;

public class ExceptionallyExample {

  private final HelloWorldService helloWorldService;

  public ExceptionallyExample(final HelloWorldService helloWorldService) {this.helloWorldService = helloWorldService;}

  public String helloWorld() {
    CommonUtil.startTimer();

    final CompletableFuture<String> hello = CompletableFuture.supplyAsync(this.helloWorldService::hello);
    final CompletableFuture<String> world = CompletableFuture.supplyAsync(this.helloWorldService::world);
    final CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
      CommonUtil.delay(1000);
      return "hi";
    });

    final var result = hello
      .exceptionally(e -> {
        LoggerUtil.log("Handle hello() Exception is: " + e.getMessage());
        return "";
      })
      .thenCombine(world, (x, y) -> x + " " + y)
      .exceptionally(e -> {
        LoggerUtil.log("handle world() Exception is: " + e.getMessage());
        return "";
      })
      .thenCombine(hi, (a, b) -> a + " " + b)
      .thenApply(String::toUpperCase)
      .thenApply(String::strip)
      .join();

    CommonUtil.timeElapsed();
    return result;
  }

}
