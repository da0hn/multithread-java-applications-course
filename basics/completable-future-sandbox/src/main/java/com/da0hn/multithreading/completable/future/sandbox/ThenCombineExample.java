package com.da0hn.multithreading.completable.future.sandbox;

import com.da0hn.multithreading.commons.utils.CommonUtil;
import com.da0hn.multithreading.commons.utils.LoggerUtil;
import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;

public final class ThenCombineExample {

  @SneakyThrows
  public static void main(final String[] args) {
    final var service = new HelloWorldService();

    final var stringCompletableFuture = helloWorld(service);

    LoggerUtil.log(stringCompletableFuture);

    final var phrase = hiUserAndHelloWorld(service, "Gabriel");
    LoggerUtil.log(phrase);

    LoggerUtil.log("Done!");
  }

  private static String helloWorld(final HelloWorldService service) {
    CommonUtil.startTimer();
    final var hello = CompletableFuture.supplyAsync(service::hello);
    final var world = CompletableFuture.supplyAsync(service::world);

    final var helloWorld = hello.thenCombine(world, (helloString, worldString) -> helloString + " " + worldString)
      .thenApply(String::toUpperCase)
      .join();

    CommonUtil.timeElapsed();

    return helloWorld;
  }

  private static String hiUserAndHelloWorld(final HelloWorldService service, final String name) {
    CommonUtil.startTimer();
    final var hello = CompletableFuture.supplyAsync(service::hello);
    final var world = CompletableFuture.supplyAsync(service::world);
    final var hi = CompletableFuture.supplyAsync(() -> {
      CommonUtil.delay(1000);
      return "Hi! " + name;
    });

    final var phrase = hi.thenCombine(hello, (previous, current) -> previous + " " + current)
      .thenCombine(world, (previous, current) -> previous + " " + current)
      .join();
    CommonUtil.timeElapsed();
    return phrase;
  }


}
