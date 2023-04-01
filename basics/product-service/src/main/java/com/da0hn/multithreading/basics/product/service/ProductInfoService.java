package com.da0hn.multithreading.basics.product.service;

import com.da0hn.multithreading.basics.product.service.domain.ProductInfo;
import com.da0hn.multithreading.basics.product.service.domain.ProductOption;
import com.da0hn.multithreading.basics.product.service.utils.CommonUtil;

import java.util.List;

public class ProductInfoService {

  public ProductInfo retrieveProductInfo(final String productId) {
    CommonUtil.delay(1000);
    final var productOptions = List.of(
      ProductOption.builder()
        .productOptionId(1L)
        .size("64GB")
        .color("Black")
        .price(4999.99)
        .build(),
      ProductOption.builder()
        .productOptionId(2L)
        .size("128GB")
        .color("Black")
        .price(6999.99)
        .build()
    );
    return ProductInfo.builder()
      .productId(productId)
      .productOptions(productOptions)
      .build();
  }

}
