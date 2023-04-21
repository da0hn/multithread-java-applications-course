package com.da0hn.multithreading.basics.product.service;

import com.da0hn.multithreading.basics.product.service.domain.Product;
import com.da0hn.multithreading.commons.utils.CommonUtil;
import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class ProductServiceCompletableFutureImpl {

  private final ProductInfoService productInfoService;
  private final ReviewService reviewService;


  public Product retrieveProductDetails(final String productId) {
    CommonUtil.startTimer();
    final var asyncRetrieveProductInfo =
      CompletableFuture.supplyAsync(() -> this.productInfoService.retrieveProductInfo(productId));
    final var asyncRetrieveReview = CompletableFuture.supplyAsync(() -> this.reviewService.retrieveReview(productId));

    final var product = CompletableFuture.supplyAsync(() -> Product.builder().productId(productId))
      .thenCombine(asyncRetrieveReview, Product.ProductBuilder::review)
      .thenCombine(asyncRetrieveProductInfo, Product.ProductBuilder::productInfo)
      .thenApply(Product.ProductBuilder::build)
      .join();
    CommonUtil.timeElapsed();
    return product;
  }
}
