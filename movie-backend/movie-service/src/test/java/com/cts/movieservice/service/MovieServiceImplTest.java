package com.cts.movieservice.service;

import static org.junit.jupiter.api.Assertions.*;

import com.cts.movieservice.dto.Movie;
import com.cts.movieservice.dto.MovieDetails;
import com.cts.movieservice.dto.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
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
        List<Movie> movies = List.of(new Movie(), new Movie());
        ResponseEntity<List<Movie>> responseEntity = new ResponseEntity<>(movies, HttpStatus.OK);
        when(restTemplate.exchange(any(), eq(new ParameterizedTypeReference<List<Movie>>() {}))).thenReturn(responseEntity);

        // Invoking the method under test
        Object result = movieService.topMovies();

        Response response = new Response();
        response.setStatus(true);
        response.setMessage("Successful");
        response.setData(movies);

        // Verifying the response
        assertEquals(response, result);
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
