package com.da0hn.multithreading.basics.checkout.service;

import com.da0hn.multithreading.basics.checkout.service.domain.Cart;
import com.da0hn.multithreading.basics.checkout.service.domain.CartItem;
import com.da0hn.multithreading.basics.checkout.service.domain.CheckoutResponse;
import com.da0hn.multithreading.commons.utils.CommonUtil;

public class CheckoutService {

  private final PriceValidatorService priceValidatorService;


  public CheckoutService(final PriceValidatorService priceValidatorService) {
    this.priceValidatorService = priceValidatorService;
  }

  public CheckoutResponse checkout(final Cart cart) {
    CommonUtil.startTimer();
    final var expiredItems = cart.items().parallelStream()
      .map(item -> {
        final var cartItemValid = !this.priceValidatorService.isCartItemValid(item);
        return item.evaluateExpiration(cartItemValid);
      })
      .filter(CartItem::expired)
      .toList();

    if (!expiredItems.isEmpty()) {
      CommonUtil.timeElapsed();
      return CheckoutResponse.invalid(expiredItems);
    }
    CommonUtil.timeElapsed();
    return CheckoutResponse.valid();
  }

}
