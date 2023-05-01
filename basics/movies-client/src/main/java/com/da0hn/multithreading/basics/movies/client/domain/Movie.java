package com.da0hn.multithreading.basics.movies.client.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record Movie(
  MovieInfo info,
  List<Review> reviews
) {
}
