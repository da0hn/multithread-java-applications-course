package com.da0hn.multithreading.completable.future.sandbox;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Tests using whenComplete() method")
@ExtendWith(MockitoExtension.class)
class WhenCompleteExampleTest {

  @BeforeEach
  void setUp() {
  }

  @Test
  @DisplayName("Should handle exception in service.hello()")
  void test1() {
    final var service = Mockito.mock(HelloWorldService.class);

    Mockito.doThrow(new RuntimeException("A runtime error"))
      .when(service)
      .hello();
    Mockito.doCallRealMethod()
      .when(service)
      .world();

    final var sut = new WhenCompleteExample(service);

    Assertions.assertThatThrownBy(sut::helloWorld)
      .hasMessage("java.lang.RuntimeException: A runtime error")
      .isInstanceOf(RuntimeException.class);

  }

  @Test
  @DisplayName("Should handle exception in service.world()")
  void test2() {
    final var service = Mockito.mock(HelloWorldService.class);

    Mockito.doCallRealMethod()
      .when(service)
      .hello();
    Mockito.doThrow(new RuntimeException("A runtime error"))
      .when(service)
      .world();

    final var sut = new WhenCompleteExample(service);

    Assertions.assertThatThrownBy(sut::helloWorld)
      .hasMessage("java.lang.RuntimeException: A runtime error")
      .isInstanceOf(RuntimeException.class);
  }

  @Test
  @DisplayName("Should handle exception in service.hello() and service.world()")
  void test3() {
    final var service = Mockito.mock(HelloWorldService.class);

    Mockito.doThrow(new RuntimeException("A runtime error"))
      .when(service)
      .hello();
    Mockito.doThrow(new RuntimeException("A runtime error"))
      .when(service)
      .world();

    final var sut = new WhenCompleteExample(service);

    Assertions.assertThatThrownBy(sut::helloWorld)
      .hasMessage("java.lang.RuntimeException: A runtime error")
      .isInstanceOf(RuntimeException.class);
  }

  @Test
  @DisplayName("Should return hello world in upper case")
  void test4() {

    final var sut = new WhenCompleteExample(new HelloWorldService());

    final var result = sut.helloWorld();

    Assertions.assertThat(result).isEqualTo("HELLO WORLD! HI");
  }
}
