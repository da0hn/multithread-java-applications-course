package com.da0hn.multithreading.basics.product.service;


import com.da0hn.multithreading.basics.product.service.domain.Review;
import com.da0hn.multithreading.basics.product.service.utils.CommonUtil;

public class ReviewService {

  public Review retrieveReview(final String productId) {
    CommonUtil.delay(1000);
    return Review.builder()
      .numberOfReview(200)
      .overallRating(4.5)
      .build();
  }

}
