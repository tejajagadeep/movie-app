package com.cts.movieservice.controller;

import com.cts.movieservice.dto.Movie;
import com.cts.movieservice.dto.MovieDetails;
import com.cts.movieservice.dto.Response;
import com.cts.movieservice.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(MovieController.class)
class MovieControllerTest1 {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    void testTopMovies() throws Exception {
        // Mock data
        List<Movie> movies = Arrays.asList(
                new Movie(),
                new Movie()
                // Add more movies as needed
        );

        Response response = new Response();
        response.setData(movies);
        response.setMessage("Successful");
        response.setStatus(true);

        // Mock the service method to return the mock data
        when(movieService.topMovies()).thenReturn(response);

        // Perform GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/public/movie/top-100-movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        // Add more assertions as needed
    }

    @Test
    void testTopMoviesById() throws Exception {
        // Mock data
        MovieDetails movieDetails = new MovieDetails();

        // Mock the service method to return the mock data
        when(movieService.topMoviesById("1")).thenReturn(movieDetails);

        // Perform GET request with path variable and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/public/movie/top-100-movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        // Add more assertions as needed
    }
}
