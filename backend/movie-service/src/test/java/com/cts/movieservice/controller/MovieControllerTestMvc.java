package com.cts.movieservice.controller;

import com.cts.movieservice.dto.Movie;
import com.cts.movieservice.dto.MovieDetails;
import com.cts.movieservice.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(MovieController.class)
class MovieControllerTestMvc {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    public void testTopMovies() throws Exception {
        Movie movie = new Movie();
        movie.setId("1");
        movie.setTitle("Movie 1");
        when(movieService.topMovies()).thenReturn(List.of(movie));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/private/movie/top-100-movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Movie 1"));
    }

    @Test
    public void testTopMoviesById() throws Exception {
        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setId("1");
        movieDetails.setTitle("Movie 1");
        when(movieService.topMoviesById("1")).thenReturn(movieDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/private/movie/top-100-movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Movie 1"));
    }
}

