package com.da0hn.multithreading.basics.product.service.application;


import com.da0hn.multithreading.basics.product.service.*;
import com.da0hn.multithreading.commons.utils.LoggerUtil;
import lombok.SneakyThrows;


public final class ProductServiceApplication {

  @SneakyThrows
  public static void main(final String[] args) {
    final var productInfoService = new ProductInfoService();
    final var reviewService = new ReviewService();
    final var syncInventoryService = new SyncInventoryService();
    final var productService = new ProductServiceCompletableFutureImpl(productInfoService, reviewService, syncInventoryService);
    final var product = productService.syncRetrieveProductDetailsWithInventory("05041620-5731-4b7e-a0d9-1891c5800382");
    LoggerUtil.log(product);
  }

}
