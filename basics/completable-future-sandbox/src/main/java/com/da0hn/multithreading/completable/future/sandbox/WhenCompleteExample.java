package com.da0hn.multithreading.completable.future.sandbox;

import com.da0hn.multithreading.commons.utils.CommonUtil;
import com.da0hn.multithreading.commons.utils.LoggerUtil;

import java.util.concurrent.CompletableFuture;

public class WhenCompleteExample {

  private final HelloWorldService helloWorldService;

  public WhenCompleteExample(final HelloWorldService helloWorldService) {this.helloWorldService = helloWorldService;}

  public String helloWorld() {
    try {
      CommonUtil.startTimer();

      final CompletableFuture<String> hello = CompletableFuture.supplyAsync(this.helloWorldService::hello);
      final CompletableFuture<String> world = CompletableFuture.supplyAsync(this.helloWorldService::world);
      final CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
        CommonUtil.delay(1000);
        return "hi";
      });

      final var result = hello
        .whenComplete((r, e) -> {
          LoggerUtil.log("Current result: " + r);
          if(e != null) {
            LoggerUtil.log("Handle hello() Exception is: " + e.getMessage());
          }
        })
        .thenCombine(world, (previous, current) -> previous + " " + current)
        .whenComplete((r, e) -> {
          LoggerUtil.log("Current result: " + r);
          if(e != null) {
            LoggerUtil.log("Handle world() Exception is: " + e.getMessage());
          }
        })
        .thenCombine(hi, (previous, current) -> previous + " " + current)
        .thenApply(String::toUpperCase)
        .thenApply(String::strip)
        .join();

      CommonUtil.timeElapsed();
      return result;
    } catch (Exception e) {
      CommonUtil.resetTimer();
      throw new RuntimeException(e.getMessage());
    }
  }

}
