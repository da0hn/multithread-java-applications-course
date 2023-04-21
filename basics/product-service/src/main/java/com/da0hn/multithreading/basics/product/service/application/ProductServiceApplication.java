package com.da0hn.multithreading.basics.product.service.application;


import com.da0hn.multithreading.basics.product.service.ProductInfoService;
import com.da0hn.multithreading.basics.product.service.ProductServiceCompletableFutureImpl;
import com.da0hn.multithreading.basics.product.service.ProductServiceExecutorServiceImpl;
import com.da0hn.multithreading.basics.product.service.ReviewService;
import com.da0hn.multithreading.commons.utils.LoggerUtil;
import lombok.SneakyThrows;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public final class ProductServiceApplication {

  @SneakyThrows
  public static void main(final String[] args) {
    final var productInfoService = new ProductInfoService();
    final var reviewService = new ReviewService();
    final var productService = new ProductServiceCompletableFutureImpl(productInfoService, reviewService);
    final var product = productService.retrieveProductDetails("05041620-5731-4b7e-a0d9-1891c5800382");
    LoggerUtil.log(product);
  }

}
