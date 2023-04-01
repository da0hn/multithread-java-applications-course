package com.da0hn.multithreading.basics.product.service;

import com.da0hn.multithreading.basics.product.service.domain.Product;
import com.da0hn.multithreading.basics.product.service.domain.ProductInfo;
import com.da0hn.multithreading.basics.product.service.domain.Review;
import com.da0hn.multithreading.commons.utils.CommonUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductServiceThreadImpl {

  private final ProductInfoService productInfoService;
  private final ReviewService reviewService;


  public Product retrieveProductDetails(final String productId) throws InterruptedException {
    CommonUtil.startTimer();
    final var productInfoServiceRunnable = new ProductInfoServiceRunnable(this.productInfoService, productId);
    final var reviewServiceRunnable = new ReviewServiceRunnable(this.reviewService, productId);

    final Thread productInfoServiceThread = new Thread(productInfoServiceRunnable);
    final Thread reviewServiceThread = new Thread(reviewServiceRunnable);

    productInfoServiceThread.start();
    reviewServiceThread.start();

    productInfoServiceThread.join();
    reviewServiceThread.join();

    CommonUtil.timeElapsed();
    return Product.builder()
      .productId(productId)
      .productInfo(productInfoServiceRunnable.getProductInfo())
      .review(reviewServiceRunnable.getReview())
      .build();
  }

  private static class ProductInfoServiceRunnable implements Runnable {

    private final String productId;
    private final ProductInfoService productInfoService;
    private ProductInfo productInfo;

    public ProductInfoServiceRunnable(final ProductInfoService productInfoService, final String productId) {
      this.productId = productId;
      this.productInfoService = productInfoService;
    }

    @Override
    public void run() {
      this.productInfo = this.productInfoService.retrieveProductInfo(this.productId);
    }

    public ProductInfo getProductInfo() {
      return this.productInfo;
    }
  }

  private static class ReviewServiceRunnable implements Runnable {
    private final ReviewService reviewService;
    private final String productId;
    private Review review;

    public ReviewServiceRunnable(final ReviewService reviewService, final String productId) {
      this.reviewService = reviewService;
      this.productId = productId;
    }

    @Override
    public void run() {
      this.review = this.reviewService.retrieveReview(this.productId);
    }

    public Review getReview() {
      return this.review;
    }
  }
}
