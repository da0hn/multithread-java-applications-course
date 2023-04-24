package com.da0hn.multithreading.basics.product.service;

import com.da0hn.multithreading.basics.product.service.domain.Product;
import com.da0hn.multithreading.basics.product.service.domain.ProductInfo;
import com.da0hn.multithreading.basics.product.service.domain.ProductOption;
import com.da0hn.multithreading.basics.product.service.domain.Review;
import com.da0hn.multithreading.commons.utils.CommonUtil;
import com.da0hn.multithreading.commons.utils.LoggerUtil;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ProductServiceCompletableFutureImpl {

  private final ProductInfoService productInfoService;
  private final ReviewService reviewService;
  private final SyncInventoryService syncInventoryService;


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


  public Product asyncRetrieveProductDetailsWithInventory(final String productId) {
    CommonUtil.startTimer();
    final var asyncRetrieveReview = CompletableFuture.supplyAsync(() -> this.reviewService.retrieveReview(productId))
      .exceptionally(e -> { // Catch exception and provide recovery value
        LoggerUtil.log("Handled the Exception in reviewService: " + e.getMessage());
        return Review.builder()
          .numberOfReview(0)
          .overallRating(0.0)
          .build();
      });

    final var asyncRetrieveProductInfo =
      CompletableFuture.supplyAsync(() -> this.productInfoService.retrieveProductInfo(productId))
        .thenApplyAsync(productInfo -> {
          final var productOptions = this.asyncUpdateInventory(productInfo); // non blocking call
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

  private List<ProductOption> asyncUpdateInventory(final ProductInfo productInfo) {
    final List<CompletableFuture<ProductOption>> updatedProductInfoList = productInfo.productOptions().stream()
      .map(option -> CompletableFuture.supplyAsync(() -> this.syncInventoryService.addInventory(option))
        .thenApply(inventory -> option.toBuilder().inventory(inventory).build()))
      .toList();

    return updatedProductInfoList.stream()
      .map(CompletableFuture::join)
      .toList();
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
