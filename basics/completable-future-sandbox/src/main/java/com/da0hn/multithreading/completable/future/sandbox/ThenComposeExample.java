package com.da0hn.multithreading.completable.future.sandbox;

import com.da0hn.multithreading.commons.utils.LoggerUtil;
import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;

public final class ThenComposeExample {

  @SneakyThrows
  public static void main(final String[] args) {
    final var service = new HelloWorldService();

    final var stringCompletableFuture = helloWorldThenCompose(service);

    stringCompletableFuture
      .thenAccept(LoggerUtil::log)
      .join();

    LoggerUtil.log("Done!");
  }

  private static CompletableFuture<String> helloWorldThenCompose(final HelloWorldService service) {
    return CompletableFuture.supplyAsync(service::hello)
      .thenCompose(service::worldFuture); // worldFuture method depends hello method
  }
}
