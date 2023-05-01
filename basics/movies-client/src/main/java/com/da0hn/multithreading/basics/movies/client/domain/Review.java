package com.da0hn.multithreading.basics.movies.client.domain;

public record Review(
  String reviewId,
  Long movieInfoId,
  String comment,
  Double ratings
) {
}
