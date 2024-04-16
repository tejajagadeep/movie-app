package com.cts.moviebooking.service;

import com.cts.moviebooking.dto.MovieDto;
import com.cts.moviebooking.feign.MovieFeignClient;
import com.cts.moviebooking.serviceimpl.WishListMovieServiceImpl;
import com.cts.moviebooking.util.MovieDtoUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishListMovieServiceImplTest {

    @Mock
    private MovieFeignClient movieFeignClient;

    @InjectMocks
    private WishListMovieServiceImpl wishListMovieService;

    @Test
    void getTop100Movies_validData_shouldReturnMovies() {
        List<MovieDto> expectedMovies = Arrays.asList(new MovieDto(), new MovieDto());
        when(movieFeignClient.getTop100Movies()).thenReturn(expectedMovies);

        List<MovieDto> result = wishListMovieService.getTop100Movies();

        assertEquals(expectedMovies, result);
        verify(movieFeignClient, times(1)).getTop100Movies();
    }

    @Test
    void getMoviesByTitle_validData_shouldReturnMovies() {
        String title = "Test Movie";
        List<MovieDto> expectedMovies = Arrays.asList(new MovieDto(), new MovieDto());
        when(movieFeignClient.getMoviesByTitle(title)).thenReturn(expectedMovies);

        List<MovieDto> result = wishListMovieService.getMoviesByTitle(title);

        assertEquals(expectedMovies, result);
        verify(movieFeignClient, times(1)).getMoviesByTitle(title);
    }

//    @Test
//    void getMoviesByRating_validData_shouldReturnUniqueMovies() {
//        double minRating = 3.0;
//        double maxRating = 5.0;
//        List<MovieDto> inputMovies = Arrays.asList(new MovieDto("Movie A", 4.0), new MovieDto("Movie B", 4.5));
//        when(movieFeignClient.getMoviesByRating(minRating, maxRating)).thenReturn(inputMovies);
//
//        List<MovieDto> result = wishListMovieService.getMoviesByRating(minRating, maxRating);
//
//        assertEquals(Arrays.asList(new MovieDto("Movie A", 4.0), new MovieDto("Movie B", 4.5)), result);
//        verify(movieFeignClient, times(1)).getMoviesByRating(minRating, maxRating);
//    }

    @Test
    void getMoviesByGenres_validData_shouldReturnMovies() {
        List<String> genres = Arrays.asList("Action", "Drama");
        List<MovieDto> expectedMovies = Arrays.asList(new MovieDto(), new MovieDto());
        when(movieFeignClient.getMoviesByGenres(genres)).thenReturn(expectedMovies);

        List<MovieDto> result = wishListMovieService.getMoviesByGenres(genres);

        assertEquals(expectedMovies, result);
        verify(movieFeignClient, times(1)).getMoviesByGenres(genres);
    }
}
