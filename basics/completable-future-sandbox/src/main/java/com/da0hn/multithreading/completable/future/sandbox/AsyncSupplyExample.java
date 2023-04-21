package com.da0hn.multithreading.completable.future.sandbox;

import com.da0hn.multithreading.commons.utils.LoggerUtil;
import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;

public final class AsyncSupplyExample {

  @SneakyThrows
  public static void main(final String[] args) {
    final var service = new HelloWorldService();

    CompletableFuture.supplyAsync(service::hello) // delayed by 1s
      .thenAccept(LoggerUtil::log) // perform action with supplied value
      .join(); // blocking main thread


    LoggerUtil.log("Done!");
  }

}
