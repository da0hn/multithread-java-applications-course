package com.da0hn.multithreading.basics.product.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductServiceTest {


  @Test
  @DisplayName("Should retrieve product details using CompletableFuture")
  void test1() {

    final var productInfoService = new ProductInfoService();
    final var reviewService = new ReviewService();
    final var syncInventoryService = new SyncInventoryService();
    final var asyncInventoryService = new AsyncInventoryService();
    final var productService = new ProductServiceCompletableFutureImpl(productInfoService, reviewService, syncInventoryService, asyncInventoryService);

    final var product = productService.syncRetrieveProductDetails("05041620-5731-4b7e-a0d9-1891c5800382");

    Assertions.assertThat(product).isNotNull();
    Assertions.assertThat(product.productInfo().productOptions()).isNotEmpty();
    Assertions.assertThat(product.review()).isNotNull();
  }


  @Test
  @DisplayName("Should retrieve a completable future product details using CompletableFuture")
  void test2() {

    final var productInfoService = new ProductInfoService();
    final var reviewService = new ReviewService();
    final var syncInventoryService = new SyncInventoryService();
    final var asyncInventoryService = new AsyncInventoryService();
    final var productService = new ProductServiceCompletableFutureImpl(productInfoService, reviewService, syncInventoryService, asyncInventoryService);

    final var asyncProductDetail = productService.asyncRetrieveProductDetails("05041620-5731-4b7e-a0d9-1891c5800382");

    asyncProductDetail.thenAccept(product -> {
        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.productInfo().productOptions()).isNotEmpty();
        Assertions.assertThat(product.review()).isNotNull();
      })
      .join();
  }

  @Test
  @DisplayName("Should retrieve product details with inventory using CompletableFuture")
  void test3() {

    final var productInfoService = new ProductInfoService();
    final var reviewService = new ReviewService();
    final var syncInventoryService = new SyncInventoryService();
    final var asyncInventoryService = new AsyncInventoryService();
    final var productService = new ProductServiceCompletableFutureImpl(productInfoService, reviewService, syncInventoryService, asyncInventoryService);
    final var product = productService.syncRetrieveProductDetailsWithInventory("05041620-5731-4b7e-a0d9-1891c5800382");

    Assertions.assertThat(product).isNotNull();
    Assertions.assertThat(product.productInfo().productOptions()).isNotEmpty();
    Assertions.assertThat(product.review()).isNotNull();
    product.productInfo().productOptions().forEach(option ->
      Assertions.assertThat(option.inventory())
    );
  }

  @Test
  @DisplayName("Should retrieve product details with inventory using CompletableFuture (Performance Improved)")
  void test4() {

    final var productInfoService = new ProductInfoService();
    final var reviewService = new ReviewService();
    final var syncInventoryService = new SyncInventoryService();
    final var asyncInventoryService = new AsyncInventoryService();
    final var productService = new ProductServiceCompletableFutureImpl(productInfoService, reviewService, syncInventoryService, asyncInventoryService);
    final var product = productService.asyncRetrieveProductDetailsWithInventory("05041620-5731-4b7e-a0d9-1891c5800382");

    Assertions.assertThat(product).isNotNull();
    Assertions.assertThat(product.productInfo().productOptions()).isNotEmpty();
    Assertions.assertThat(product.review()).isNotNull();
    product.productInfo().productOptions().forEach(option ->
      Assertions.assertThat(option.inventory())
    );
  }
}
