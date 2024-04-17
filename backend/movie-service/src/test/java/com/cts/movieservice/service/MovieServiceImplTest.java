package com.cts.movieservice.service;

import static org.junit.jupiter.api.Assertions.*;

import com.cts.movieservice.dto.Movie;
import com.cts.movieservice.dto.MovieDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
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
        // Mocking the response entity
        Movie[] movies = {new Movie(), new Movie()};
        ResponseEntity<Movie[]> responseEntity = new ResponseEntity<>(movies, HttpStatus.OK);
        when(restTemplate.exchange(any(), eq(Movie[].class))).thenReturn(responseEntity);

        // Invoking the method under test
        Object result = movieService.topMovies();

        // Verifying the response
        assertEquals(movies, result);
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
