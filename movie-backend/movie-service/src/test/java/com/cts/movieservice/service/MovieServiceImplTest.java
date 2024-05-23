package com.cts.movieservice.service;

import com.cts.movieservice.dto.Movie;
import com.cts.movieservice.dto.MovieDetails;
import com.cts.movieservice.dto.MovieResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTopMovies() {
        // Mocking the movieResponse entity
        List<Movie> movies = List.of(new Movie(), new Movie());
        ResponseEntity<List<Movie>> responseEntity = new ResponseEntity<>(movies, HttpStatus.OK);
        when(restTemplate.exchange(any(), eq(new ParameterizedTypeReference<List<Movie>>() {}))).thenReturn(responseEntity);

        // Invoking the method under test
        Object result = movieService.topMovies();

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setStatus(true);
        movieResponse.setMessage("Successful");
        movieResponse.setData(movies);

        // Verifying the movieResponse
        assertEquals(movieResponse, result);
    }

    @Test
    void testTopMoviesSearch() {
        // Mocking the movieResponse entity
        Movie movie = new Movie();
        movie.setTitle("godfather");
        List<Movie> movies = List.of(movie);
        ResponseEntity<List<Movie>> responseEntity = new ResponseEntity<>(movies, HttpStatus.OK);
        when(restTemplate.exchange(any(), eq(new ParameterizedTypeReference<List<Movie>>() {}))).thenReturn(responseEntity);

        // Invoking the method under test
        MovieResponse movieResponse = movieService.topMoviesSearch("godfather");

        MovieResponse expectedMovieResponse = new MovieResponse();
        expectedMovieResponse.setStatus(true);
        expectedMovieResponse.setMessage("Successful");
        expectedMovieResponse.setData(movies);

        // Verifying the movieResponse
        assertEquals(expectedMovieResponse, movieResponse);
    }

    @Test
    void testTopMoviesFilterGenre() {
        // Mocking the movieResponse entity
        Movie movie = new Movie();
        movie.setGenre(List.of("Action"));
        List<Movie> movies = List.of(movie);
        ResponseEntity<List<Movie>> responseEntity = new ResponseEntity<>(movies, HttpStatus.OK);
        when(restTemplate.exchange(any(), eq(new ParameterizedTypeReference<List<Movie>>() {}))).thenReturn(responseEntity);

        // Invoking the method under test
        MovieResponse movieResponse = movieService.topMoviesByGenre("Action");

        MovieResponse expectedMovieResponse = new MovieResponse();
        expectedMovieResponse.setStatus(true);
        expectedMovieResponse.setMessage("Successful");
        expectedMovieResponse.setData(movies);

        // Verifying the movieResponse
        assertEquals(expectedMovieResponse, movieResponse);
    }


    @Test
    void testTopMoviesById() {
        // Mocking the response entity
        MovieDetails movieDetails = new MovieDetails();
        ResponseEntity<MovieDetails> responseEntity = new ResponseEntity<>(movieDetails, HttpStatus.OK);
        when(restTemplate.exchange(any(), eq(MovieDetails.class))).thenReturn(responseEntity);

        // Invoking the method under test
        Object result = movieService.topMoviesById("123");

        // Verifying the response
        assertEquals(movieDetails, result);
    }

}