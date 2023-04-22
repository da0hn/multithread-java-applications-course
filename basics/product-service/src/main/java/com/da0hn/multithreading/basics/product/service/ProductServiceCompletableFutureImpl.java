package com.da0hn.multithreading.basics.product.service;

import com.da0hn.multithreading.basics.product.service.domain.Product;
import com.da0hn.multithreading.basics.product.service.domain.ProductInfo;
import com.da0hn.multithreading.basics.product.service.domain.ProductOption;
import com.da0hn.multithreading.commons.utils.CommonUtil;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ProductServiceCompletableFutureImpl {

  private final ProductInfoService productInfoService;
  private final ReviewService reviewService;
  private final SyncInventoryService syncInventoryService;
  private final AsyncInventoryService asyncInventoryService;


  public Product syncRetrieveProductDetails(final String productId) {
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


  public CompletableFuture<Product> asyncRetrieveProductDetails(final String productId) {
    final var asyncRetrieveProductInfo =
      CompletableFuture.supplyAsync(() -> this.productInfoService.retrieveProductInfo(productId));
    final var asyncRetrieveReview = CompletableFuture.supplyAsync(() -> this.reviewService.retrieveReview(productId));

    return CompletableFuture.supplyAsync(() -> Product.builder().productId(productId))
      .thenCombine(asyncRetrieveReview, Product.ProductBuilder::review)
      .thenCombine(asyncRetrieveProductInfo, Product.ProductBuilder::productInfo)
      .thenApply(Product.ProductBuilder::build);
  }

  public Product syncRetrieveProductDetailsWithInventory(final String productId) {
    CommonUtil.startTimer();
    final var asyncRetrieveReview = CompletableFuture.supplyAsync(() -> this.reviewService.retrieveReview(productId));

    final var asyncRetrieveProductInfo =
      CompletableFuture.supplyAsync(() -> this.productInfoService.retrieveProductInfo(productId))
      .thenApply(productInfo -> {
        final var productOptions = this.syncUpdateInventory(productInfo); // blocking call
        return productInfo.toBuilder()
          .productOptions(productOptions)
          .build();
      });

    final var product = CompletableFuture.supplyAsync(() -> Product.builder().productId(productId))
      .thenCombine(asyncRetrieveReview, Product.ProductBuilder::review)
      .thenCombine(asyncRetrieveProductInfo, Product.ProductBuilder::productInfo)
      .thenApply(Product.ProductBuilder::build)
      .join();

    CommonUtil.timeElapsed();
    return product;
  }

  private List<ProductOption> syncUpdateInventory(final ProductInfo productInfo) {
    return productInfo.productOptions().stream()
      .map(option -> {
        final var inventory = this.syncInventoryService.addInventory(option);
        return option.toBuilder()
          .inventory(inventory)
          .build();
      }).collect(Collectors.toList());
  }
}
