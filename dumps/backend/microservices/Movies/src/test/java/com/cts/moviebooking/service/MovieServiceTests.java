package com.cts.moviebooking.service;

import com.cts.moviebooking.dto.Movie;
import com.cts.moviebooking.entity.MovieEntity;
import com.cts.moviebooking.exception.MovieBookingException;
import com.cts.moviebooking.repository.MovieRepo;
import com.cts.moviebooking.service.MovieService;
import com.cts.moviebooking.serviceimpl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTests {

//    @Mock
//    private MovieRepo movieRepo;
//
//
//
//    @Value("${rapidapi.key}")
//    private String rapidApiKey;
//
//    @InjectMocks
//    private MovieServiceImpl movieService;
//
//    private MockMvc mockMvc;
//
//    List<Movie> movies = new ArrayList<>();
//
//    List<MovieEntity> movieEntities = new ArrayList<>();
//
////    @BeforeEach
////    public void setUp() {
////        mockMvc = MockMvcBuilders.standaloneSetup(movieService).build();
////
////        Movie movie1 = new Movie(1, "Title 1", "Description 1", "image1.jpg", "bigImage1.jpg", "Genre1", "thumbnail1.jpg", 8.5, "id1", 2021, "imdbid1", "imdbLink1");
////        Movie movie2 = new Movie(2, "Title 2", "Description 2", "image2.jpg", "bigImage2.jpg", "Genre2", "thumbnail2.jpg", 9.0, "id2", 2022, "imdbid2", "imdbLink2");
////        Movie movie3 = new Movie(3, "Title 3", "Description 3", "image3.jpg", "bigImage3.jpg", "Genre3", "thumbnail3.jpg", 7.8, "id3", 2023, "imdbid3", "imdbLink3");
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
//    @Test
//    public void testGetMoviesByTitle() {
//
//        // Mock repository
//        String titleToSearch = "Title 1";
//
//        // Mock repository
//        when(movieRepo.findByTitle(argThat(title -> title.equals(titleToSearch)))).thenReturn(Arrays.asList(movieEntities.get(0))); // Return only the first movie with the specified title
//
//        // Test service method
//        List<MovieEntity> result = movieService.getMoviesByTitle(titleToSearch);
//
//        // Assertions
//        assertNotNull(result);
//        assertEquals(1, result.size()); // Ensure only one movie is returned
//        assertEquals(titleToSearch, result.get(0).getTitle()); // Ensure the returned movie has the correct title
//
//    }
//
//
////    @Test
////    public void testGetMoviesByRating() {
////        // Mock repository
////        when(movieRepo.findByRatingBetween(anyDouble(), anyDouble()))
////                .thenReturn(movieEntities);
////
////        // Test service method
////        List<MovieEntity> result = movieService.getMoviesByRating(1.0, 10.0);
////
////        // Assertions
////        assertNotNull(result);
////
////    }
//
//
//    @Test
//    public void testGetMoviesByGenres() {
//        // Mock repository
//        when(movieRepo.findByGenreIn(anyList())).thenReturn(movieEntities);
//
//        // Test service method
//        List<MovieEntity> result = movieService.getMoviesByGenres(Arrays.asList("Drama","Action"));
//
//        // Assertions
//        assertNotNull(result);
//        // Add more assertions based on your actual implementation
//    }
}