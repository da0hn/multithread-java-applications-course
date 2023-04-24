package com.da0hn.multithreading.completable.future.sandbox;

import com.da0hn.multithreading.commons.utils.CommonUtil;
import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@AllArgsConstructor
public final class AsyncOverloadedCompletableFutureMethodsExample {

  private final HelloWorldService helloWorldService;

  public String asyncHelloWorld() {
    CommonUtil.startTimer();

    final CompletableFuture<String> hello = CompletableFuture.supplyAsync(this.helloWorldService::hello);
    final CompletableFuture<String> world = CompletableFuture.supplyAsync(this.helloWorldService::world);
    final CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
      CommonUtil.delay(1000);
      return "hi";
    });

    final var result = hello
      .thenCombineAsync(world, (x, y) -> x + " " + y)
      .thenCombineAsync(hi, (a, b) -> a + " " + b)
      .thenApplyAsync(String::toUpperCase)
      .thenApplyAsync(String::strip)
      .join();

    CommonUtil.timeElapsed();
    return result;
  }

  public String asyncCustomThreadPoolHelloWorld() {
    CommonUtil.startTimer();
    final var executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    final CompletableFuture<String> hello = CompletableFuture.supplyAsync(this.helloWorldService::hello);
    final CompletableFuture<String> world = CompletableFuture.supplyAsync(this.helloWorldService::world);
    final CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
      CommonUtil.delay(1000);
      return "hi";
    });

    final var result = hello
      .thenCombineAsync(world, (x, y) -> x + " " + y, executorService)
      .thenCombineAsync(hi, (a, b) -> a + " " + b,  executorService)
      .thenApplyAsync(String::toUpperCase,  executorService)
      .thenApplyAsync(String::strip,  executorService)
      .join();

    CommonUtil.timeElapsed();
    return result;
  }

}
