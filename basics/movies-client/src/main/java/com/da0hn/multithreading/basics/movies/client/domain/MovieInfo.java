package com.da0hn.multithreading.basics.movies.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public record MovieInfo(
  Long movieInfoId,
  String name,
  Integer year,
  List<String> cast,
  @JsonProperty("release_date")
  LocalDate releaseDate
) {
}
