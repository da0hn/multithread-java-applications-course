package com.da0hn.multithreading.basics.movies.client.domain;

import java.time.LocalDate;
import java.util.List;

public record MovieInfo(
  Long movieInfoId,
  String name,
  Integer year,
  List<String> cast,
  LocalDate releaseDate
) {
}
