package com.cts.moviebooking.controller;

import com.cts.moviebooking.dto.MovieDto;
import com.cts.moviebooking.exception.WishlistException;
import com.cts.moviebooking.service.WishlistMovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishListMovieControllerTest {

    @Mock
    private WishlistMovieService wishlistMovieService;

    @InjectMocks
    private WishListMovieController wishListMovieController;

    @Test
    void getTop100Movies_validData_shouldReturnOk() throws WishlistException {
        List<MovieDto> movies = Arrays.asList(new MovieDto(), new MovieDto());
        when(wishlistMovieService.getTop100Movies()).thenReturn(movies);

        ResponseEntity<?> responseEntity = wishListMovieController.getTop100Movies();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getTop100Movies_exceptionThrown_shouldReturnInternalServerError() throws WishlistException {
        when(wishlistMovieService.getTop100Movies()).thenThrow(new WishlistException("Test Exception"));

        ResponseEntity<?> responseEntity = wishListMovieController.getTop100Movies();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getMoviesByTitle_validData_shouldReturnOk() throws WishlistException {
        String title = "Test Movie";
        List<MovieDto> movies = Arrays.asList(new MovieDto(), new MovieDto());
        when(wishlistMovieService.getMoviesByTitle(title)).thenReturn(movies);

        ResponseEntity<?> responseEntity = wishListMovieController.getMoviesByTitle(title);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getMoviesByTitle_exceptionThrown_shouldReturnInternalServerError() throws WishlistException {
        String title = "Test Movie";
        when(wishlistMovieService.getMoviesByTitle(title)).thenThrow(new WishlistException("Test Exception"));

        ResponseEntity<?> responseEntity = wishListMovieController.getMoviesByTitle(title);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getMoviesByRating_validData_shouldReturnOk() throws WishlistException {
        double minRating = 3.0;
        double maxRating = 5.0;
        List<MovieDto> movies = Arrays.asList(new MovieDto(), new MovieDto());
        when(wishlistMovieService.getMoviesByRating(minRating, maxRating)).thenReturn(movies);

        ResponseEntity<?> responseEntity = wishListMovieController.getMoviesByRating(minRating, maxRating);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getMoviesByRating_exceptionThrown_shouldReturnInternalServerError() throws WishlistException {
        double minRating = 3.0;
        double maxRating = 5.0;
        when(wishlistMovieService.getMoviesByRating(minRating, maxRating))
                .thenThrow(new WishlistException("Test Exception"));

        ResponseEntity<?> responseEntity = wishListMovieController.getMoviesByRating(minRating, maxRating);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getMoviesByGenres_validData_shouldReturnOk() throws WishlistException {
        List<String> genres = Arrays.asList("Action", "Drama");
        List<MovieDto> movies = Arrays.asList(new MovieDto(), new MovieDto());
        when(wishlistMovieService.getMoviesByGenres(genres)).thenReturn(movies);

        ResponseEntity<?> responseEntity = wishListMovieController.getMoviesByGenres(genres);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getMoviesByGenres_exceptionThrown_shouldReturnInternalServerError() throws WishlistException {
        List<String> genres = Arrays.asList("Action", "Drama");
        when(wishlistMovieService.getMoviesByGenres(genres)).thenThrow(new WishlistException("Test Exception"));

        ResponseEntity<?> responseEntity = wishListMovieController.getMoviesByGenres(genres);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }
}
