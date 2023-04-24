package com.da0hn.multithreading.completable.future.sandbox;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test completable future async overload methods")
class AsyncOverloadedCompletableFutureMethodsExampleTest {


  @Test
  @DisplayName("Should return hello world in upper case")
  void test1() {

    final var sut = new AsyncOverloadedCompletableFutureMethodsExample(new HelloWorldService());

    final var result = sut.asyncHelloWorld();

    Assertions.assertThat(result).isEqualTo("HELLO WORLD! HI");
  }


  @Test
  @DisplayName("Should return hello world in upper case using custom ThreadPool")
  void test2() {

    final var sut = new AsyncOverloadedCompletableFutureMethodsExample(new HelloWorldService());

    final var result = sut.asyncCustomThreadPoolHelloWorld();

    Assertions.assertThat(result).isEqualTo("HELLO WORLD! HI");
  }
}
