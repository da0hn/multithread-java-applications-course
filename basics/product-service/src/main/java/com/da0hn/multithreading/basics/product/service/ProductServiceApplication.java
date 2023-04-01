package com.da0hn.multithreading.basics.product.service;


import com.da0hn.multithreading.commons.utils.LoggerUtil;

public class ProductServiceApplication {

  public static void main(final String[] args) {
    final var productInfoService = new ProductInfoService();
    final var reviewService = new ReviewService();
    final var productService = new ProductService(productInfoService, reviewService);
    final var product = productService.retrieveProductDetails("05041620-5731-4b7e-a0d9-1891c5800382");
    LoggerUtil.log(product);
  }

}
