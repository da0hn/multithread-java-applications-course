package com.da0hn.multithreading.basics.product.service;

import com.da0hn.multithreading.basics.product.service.domain.Product;
import com.da0hn.multithreading.basics.product.service.domain.ProductInfo;
import com.da0hn.multithreading.basics.product.service.domain.Review;
import com.da0hn.multithreading.commons.utils.CommonUtil;
import lombok.AllArgsConstructor;

import java.util.concurrent.*;

@AllArgsConstructor
public class ProductServiceExecutorServiceImpl {

  private final ExecutorService executorService = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors()
  );

  private final ProductInfoService productInfoService;
  private final ReviewService reviewService;


  public Product retrieveProductDetails(final String productId) throws ExecutionException, InterruptedException, TimeoutException {
    CommonUtil.startTimer();

    final Future<ProductInfo> futureProductInfo = this.executorService.submit(() -> this.productInfoService.retrieveProductInfo(productId));
    final Future<Review> futureReview = this.executorService.submit(() -> this.reviewService.retrieveReview(productId));

    final var productInfo = futureProductInfo.get(1500, TimeUnit.MILLISECONDS);
    final var review = futureReview.get(1500, TimeUnit.MILLISECONDS);

    CommonUtil.timeElapsed();

    this.executorService.shutdownNow();

    return Product.builder()
      .productId(productId)
      .productInfo(productInfo)
      .review(review)
      .build();
  }
}
