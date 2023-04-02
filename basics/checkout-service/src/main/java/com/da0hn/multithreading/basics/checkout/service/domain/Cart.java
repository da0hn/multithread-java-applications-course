package com.da0hn.multithreading.basics.checkout.service.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record Cart(Integer cartId, List<CartItem> items) {

}
