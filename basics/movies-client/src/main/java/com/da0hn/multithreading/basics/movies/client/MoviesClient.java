package com.da0hn.multithreading.basics.movies.client;

import com.da0hn.multithreading.basics.movies.client.domain.Movie;
import com.da0hn.multithreading.basics.movies.client.domain.MovieInfo;
import com.da0hn.multithreading.basics.movies.client.domain.Review;
import com.da0hn.multithreading.commons.utils.CommonUtil;
import com.da0hn.multithreading.commons.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class MoviesClient {

  private final WebClient webClient;

  public Movie syncRetrieveMovie(final Long movieInfoId) {
    CommonUtil.startTimer();

    final var movieInfo = this.syncInvokeMovieInfoService(movieInfoId);
    final var reviews = this.syncInvokeReviewService(movieInfoId);

    final var movie = new Movie(
      movieInfo,
      reviews
    );

    CommonUtil.timeElapsed();

    return movie;
  }


  public Movie asyncRetrieveMovie(final Long movieInfoId) {
    CommonUtil.startTimer();
    final var movieInfo = CompletableFuture.supplyAsync(() -> this.syncInvokeMovieInfoService(movieInfoId));
    final var reviews = CompletableFuture.supplyAsync(() -> this.syncInvokeReviewService(movieInfoId));

    final var movie = CompletableFuture.supplyAsync(Movie::builder)
      .thenCombine(movieInfo, Movie.MovieBuilder::info)
      .thenCombine(reviews, Movie.MovieBuilder::reviews)
      .thenApply(Movie.MovieBuilder::build)
      .join();

    CommonUtil.timeElapsed();
    return movie;
  }


  private MovieInfo syncInvokeMovieInfoService(final Long movieInfoId) {
    final String resourcePath = "/v1/movie_infos/{movieInfoId}";

    LoggerUtil.log("Retrieving movie info " + movieInfoId);

    final var movieInfo = this.webClient.get()
      .uri(resourcePath, movieInfoId)
      .retrieve()
      .bodyToMono(MovieInfo.class)
      .block();

    LoggerUtil.log("Movie info successfully retrieved " + movieInfo);

    return movieInfo;
  }


  private List<Review> syncInvokeReviewService(final Long movieInfoId) {

    final var resourcePath = UriComponentsBuilder.fromUriString("/v1/reviews")
      .queryParam("movieInfoId", movieInfoId)
      .buildAndExpand()
      .toString();

    LoggerUtil.log("Retrieving reviews of movie info " + movieInfoId);

    final var reviews = this.webClient.get()
      .uri(resourcePath)
      .retrieve()
      .bodyToFlux(Review.class)
      .collectList()
      .block();

    LoggerUtil.log("Reviews successfully retrieved " + reviews);

    return reviews;
  }

}
