package com.cts.movieservice.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.cts.movieservice.dto.Movie;
import com.cts.movieservice.dto.MovieDetails;
import com.cts.movieservice.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTopMovies() {
        Movie[] movies = {new Movie(), new Movie()};
        when(movieService.topMovies()).thenReturn(movies);

        ResponseEntity<Object> responseEntity = movieController.topMovies();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(movies, responseEntity.getBody());
    }

    @Test
    void testTopMoviesById() {
        MovieDetails movieDetails = new MovieDetails();
        when(movieService.topMoviesById(anyString())).thenReturn(movieDetails);

        ResponseEntity<Object> responseEntity = movieController.topMoviesById("123");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(movieDetails, responseEntity.getBody());
    }
}
