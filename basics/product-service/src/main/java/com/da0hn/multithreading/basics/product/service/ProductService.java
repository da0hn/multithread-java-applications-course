package com.da0hn.multithreading.basics.product.service;

import com.da0hn.multithreading.basics.product.service.domain.Product;
import com.da0hn.multithreading.basics.product.service.utils.CommonUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductService {

  private final ProductInfoService productInfoService;
  private final ReviewService reviewService;


  public Product retrieveProductDetails(final String productId) {
    CommonUtil.startTimer();
    final var productInfo = this.productInfoService.retrieveProductInfo(productId);
    final var review = this.reviewService.retrieveReview(productId);
    CommonUtil.timeElapsed();
    return Product.builder()
      .productId(productId)
      .productInfo(productInfo)
      .review(review)
      .build();
  }
}
