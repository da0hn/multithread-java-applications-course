package com.da0hn.multithreading.completable.future.sandbox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

class SupplyAsyncAndThenApplyExampleTest {

  @Test
  @DisplayName("Test async CompletableFuture value")
  void test1() {
    final var sut = new SupplyAsyncAndThenApplyExample();
    final var service = new HelloWorldService();
    final CompletableFuture<String> asyncHelloString = sut.helloWorld(service);
    asyncHelloString
      .thenAccept(value -> Assertions.assertEquals("HELLO WORLD", value))
      .join(); // block main thread
  }
}
