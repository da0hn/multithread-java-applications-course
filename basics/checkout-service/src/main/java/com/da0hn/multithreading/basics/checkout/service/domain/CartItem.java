package com.da0hn.multithreading.basics.checkout.service.domain;

import lombok.Builder;

@Builder(toBuilder = true)
public record CartItem(Long itemId, String name, Double rate, Integer quantity, boolean expired) {

  public CartItem evaluateExpiration(final boolean expired) {
    return this.toBuilder()
      .expired(expired)
      .build();
  }
}
