package com.cts.moviebooking.controller;

import com.cts.moviebooking.dto.Movie;
import com.cts.moviebooking.entity.MovieEntity;
import com.cts.moviebooking.service.MovieService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTests {
//
//    @Mock
//    private MovieService movieService;
//
//    @InjectMocks
//    private MovieController movieController;
//
//    private MockMvc mockMvc;
//
//    List<Movie> movies = new ArrayList<>();
//
//    List<MovieEntity> movieEntities = new ArrayList<>();
//
////    @BeforeEach
////    public void setUp() {
////        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
////
////        Movie movie1 = new Movie(1, "Title 1", "Description 1", "image1.jpg", "bigImage1.jpg", "Genre1", "thumbnail1.jpg", "8.5", "id1", 2021, "imdbid1", "imdbLink1");
////        Movie movie2 = new Movie(2, "Title 2", "Description 2", "image2.jpg", "bigImage2.jpg", "Genre2", "thumbnail2.jpg", "9.0", "id2", 2022, "imdbid2", "imdbLink2");
////        Movie movie3 = new Movie(3, "Title 3", "Description 3", "image3.jpg", "bigImage3.jpg", "Genre3", "thumbnail3.jpg", "7.8", "id3", 2023, "imdbid3", "imdbLink3");
////
////
////        movies.add(movie1);
////        movies.add(movie2);
////        movies.add(movie3);
////
////        MovieEntity movie11 = new MovieEntity("id1", "Title 1", "Description 1", "image1.jpg", "Genre1", 8.5, 2021, "imdbid1", "imdbLink1");
////        MovieEntity movie22 = new MovieEntity("id2", "Title 2", "Description 2", "image2.jpg", "Genre2", 9.0, 2022, "imdbid2", "imdbLink2");
////        MovieEntity movie33 = new MovieEntity("id3", "Title 3", "Description 3", "image3.jpg", "Genre3", 7.8, 2023, "imdbid3", "imdbLink3");
////
////        movieEntities.add(movie11);
////        movieEntities.add(movie22);
////        movieEntities.add(movie33);
////    }
//
//
//
//    @Test
//    public void testGetTop100Movies() throws Exception {
//
//        when(movieService.getTop100Movies()).thenReturn(movies);
//
//
//        mockMvc.perform(get("/api/v1/movies/top100"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0]").exists())
//                .andExpect(jsonPath("$[0].title").exists());
//
//        verify(movieService).getTop100Movies();
//    }
//
//    @Test
//    public void testGetMoviesByTitle() throws Exception {
//        // Mock service response
//        when(movieService.getMoviesByTitle(Mockito.anyString())).thenReturn(movieEntities);
//
//        // Perform and verify the request
//        mockMvc.perform(get("/api/v1/movies/byTitle").param("title", "yourTitle"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0]").exists())
//                .andExpect(jsonPath("$[0].title").exists());
//
//        // Verify service method was called
//        verify(movieService).getMoviesByTitle("yourTitle");
//    }
//
////    @Test
////    public void testGetMoviesByRating() throws Exception {
////        // Mock service response
////        when(movieService.getMoviesByRating(Mockito.anyDouble(), Mockito.anyDouble()))
////                .thenReturn(movieEntities);
////
////        // Perform and verify the request
////        mockMvc.perform(get("/api/v1/movies/byRating")
////                        .param("minRating", "1.0")
////                        .param("maxRating", "10.0"))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$[0]").exists())
////                .andExpect(jsonPath("$[0].title").exists());
////
////        // Verify service method was called
////        verify(movieService).getMoviesByRating(1.0, 10.0);
////    }
//
//    @Test
//    public void testGetMoviesByGenres() throws Exception {
//        // Mock service response
//        when(movieService.getMoviesByGenres(Mockito.anyList())).thenReturn(movieEntities);
//
//        // Perform and verify the request
//        mockMvc.perform(get("/api/v1/movies/byGenres").param("genres", "genre1,genre2"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0]").exists())
//                .andExpect(jsonPath("$[0].title").exists());
//
//        // Verify service method was called
//        verify(movieService).getMoviesByGenres(Arrays.asList("genre1", "genre2"));
//    }
}