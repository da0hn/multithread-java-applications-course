package com.da0hn.multithreading.basics.product.service.domain;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record ProductInfo(
  String productId,
  List<ProductOption> productOptions
) {
}
