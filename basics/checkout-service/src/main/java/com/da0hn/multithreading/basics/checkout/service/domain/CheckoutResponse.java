package com.da0hn.multithreading.basics.checkout.service.domain;

import java.util.List;

public record CheckoutResponse(
  CheckoutStatus status,
  List<CartItem> invalidItems
) {

  public static CheckoutResponse invalid(final List<CartItem> items) {
    return new CheckoutResponse(CheckoutStatus.FAILURE, items);
  }

  public static CheckoutResponse valid() {
    return new CheckoutResponse(CheckoutStatus.SUCCESS, null);
  }
}
