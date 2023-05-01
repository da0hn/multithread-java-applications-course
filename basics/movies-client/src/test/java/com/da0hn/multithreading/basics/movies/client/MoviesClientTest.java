package com.da0hn.multithreading.basics.movies.client;


import com.da0hn.multithreading.basics.movies.client.domain.Movie;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@DisplayName("Sync/Async Movie REST client tests")
class MoviesClientTest {

  private WebClient webClient;

  @BeforeEach
  void setUp() {
    this.webClient = WebClient.builder()
      .baseUrl("http://localhost:8080/movies")
      .build();
  }

  @Test
  @DisplayName("Should synchronous retrieve Movie Info by id")
  void test1() {

    final var moviesClient = new MoviesClient(this.webClient);

    final var movie = moviesClient.syncRetrieveMovie(1L);

    final ThrowingConsumer<Movie> hasReviews = m -> Assertions.assertThat(m.reviews()).hasSize(1);
    final ThrowingConsumer<Movie> hasMovieInfo = m -> Assertions.assertThat(m.info()).isNotNull();


    Assertions.assertThat(movie)
      .isNotNull()
      .satisfies(hasReviews, hasMovieInfo);
    Assertions.assertThat(movie)
      .extracting(
        m -> m.info().movieInfoId(),
        m -> m.info().name(),
        m -> m.info().year(),
        m -> m.info().releaseDate()
      )
      .contains(1L, "Batman Begins", 2005, LocalDate.of(2005, 06, 15));

  }


  @Test
  @DisplayName("Should asynchronous retrieve Movie Info by id")
  void test2() {

    final var moviesClient = new MoviesClient(this.webClient);

    final var movie = moviesClient.asyncRetrieveMovie(1L);

    final ThrowingConsumer<Movie> hasReviews = m -> Assertions.assertThat(m.reviews()).hasSize(1);
    final ThrowingConsumer<Movie> hasMovieInfo = m -> Assertions.assertThat(m.info()).isNotNull();


    Assertions.assertThat(movie)
      .isNotNull()
      .satisfies(hasReviews, hasMovieInfo);
    Assertions.assertThat(movie)
      .extracting(
        m -> m.info().movieInfoId(),
        m -> m.info().name(),
        m -> m.info().year(),
        m -> m.info().releaseDate()
      )
      .contains(1L, "Batman Begins", 2005, LocalDate.of(2005, 06, 15));

  }

  @RepeatedTest(1000)
  @DisplayName("Should synchronous retrieve Movie Info by id list")
  void test3() {

    final var moviesClient = new MoviesClient(this.webClient);

    final var movies = moviesClient.syncRetrieveMovies(List.of(1L, 2L, 3L, 4L, 5L));

    Assertions.assertThat(movies)
      .isNotNull()
      .hasSize(5);

  }

  @RepeatedTest(1000)
  @DisplayName("Should asynchronous retrieve Movie Info by id list")
  void test4() {

    final var moviesClient = new MoviesClient(this.webClient);

    final var movies = moviesClient.asyncRetrieveMovies(List.of(1L, 2L, 3L, 4L, 5L));

    Assertions.assertThat(movies)
      .isNotNull()
      .hasSize(5);

  }
}
