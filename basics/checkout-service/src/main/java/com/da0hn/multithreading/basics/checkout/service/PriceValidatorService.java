package com.da0hn.multithreading.basics.checkout.service;

import com.da0hn.multithreading.basics.checkout.service.domain.CartItem;
import com.da0hn.multithreading.commons.utils.CommonUtil;

import java.util.List;

public class PriceValidatorService {

  private static final int CART_ITEM_VALIDATION_DELAY_IN_MS = 600;

  public boolean isCartItemValid(final CartItem cartItem) {
    final var cartId = cartItem.itemId();
    CommonUtil.delay(CART_ITEM_VALIDATION_DELAY_IN_MS);
    final var invalidIds = List.of(7L, 9L, 11L);
    return !invalidIds.contains(cartId);
  }

}
