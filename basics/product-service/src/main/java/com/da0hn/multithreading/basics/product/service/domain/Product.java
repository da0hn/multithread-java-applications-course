package com.da0hn.multithreading.basics.product.service.domain;

import lombok.Builder;

@Builder
public record Product(
  String productId,
  ProductInfo productInfo,
  Review review
) {
}
