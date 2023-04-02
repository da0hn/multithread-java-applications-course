package com.da0hn.multithreading.basics.checkout.service;


import com.da0hn.multithreading.basics.checkout.service.domain.CheckoutResponse;
import com.da0hn.multithreading.basics.checkout.service.domain.CheckoutStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

class CheckoutServiceTest {

  private CheckoutService sut;

  @BeforeEach
  void setUp() {
    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(Runtime.getRuntime().availableProcessors()));
    this.sut = new CheckoutService(new PriceValidatorService());
  }

  @Test
  @DisplayName("Should return SUCCESS when Cart has valid items")
  void test1() {

    final var cart = CartDataSet.createCart(5);
    final var response = this.sut.checkout(cart);

    final Consumer<CheckoutResponse> checkoutSuccessful = r -> Assertions.assertThat(r.status()).isEqualTo(CheckoutStatus.SUCCESS);
    final Consumer<CheckoutResponse> doesNotHaveInvalidItems = r -> Assertions.assertThat(r.invalidItems()).isNull();

    Assertions.assertThat(response)
      .satisfies(checkoutSuccessful, doesNotHaveInvalidItems);

  }


  @Test
  @DisplayName("Should return FAILURE when Cart has invalid items")
  void test2() {

    final var cart = CartDataSet.createCart(25);
    final var response = this.sut.checkout(cart);

    final Consumer<CheckoutResponse> checkoutFail = r -> Assertions.assertThat(r.status()).isEqualTo(CheckoutStatus.FAILURE);
    final Consumer<CheckoutResponse> hasInvalidItem = r -> Assertions.assertThat(r.invalidItems()).hasSize(3);

    Assertions.assertThat(response)
      .satisfies(checkoutFail, hasInvalidItem);

  }


  @Test
  @DisplayName("Should change parallelism workers count")
  void test3() {

    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");

    final var cart = CartDataSet.createCart(100);
    final var response = this.sut.checkout(cart);

    final Consumer<CheckoutResponse> checkoutFail = r -> Assertions.assertThat(r.status()).isEqualTo(CheckoutStatus.FAILURE);
    final Consumer<CheckoutResponse> hasInvalidItem = r -> Assertions.assertThat(r.invalidItems()).hasSize(3);

    Assertions.assertThat(response)
      .satisfies(checkoutFail, hasInvalidItem);

  }

}
