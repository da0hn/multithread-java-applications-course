package com.da0hn.multithreading.basics.checkout.service;

import com.da0hn.multithreading.basics.checkout.service.domain.Cart;
import com.da0hn.multithreading.basics.checkout.service.domain.CartItem;
import com.da0hn.multithreading.commons.data.SimpleDataSet;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class CartDataSet {

  public static Cart createCart(final int noOfItemsInCart) {
    final var items = LongStream.rangeClosed(1, noOfItemsInCart)
      .mapToObj(index -> new CartItem(
        index,
        "CartItem - ".concat(String.valueOf(index)),
        SimpleDataSet.generateRandomPrice(),
        (int) index,
        false
      )).toList();
    return new Cart(ThreadLocalRandom.current().nextInt(), items);
  }
}
