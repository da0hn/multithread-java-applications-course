package com.da0hn.multithreading.basics.movies.client.domain;

import java.util.List;

public record Movie(
  MovieInfo info,
  List<Review> reviews
) {
}
