package com.cts.moviebooking.repo;

import com.cts.moviebooking.entity.MovieEntity;
import com.cts.moviebooking.repository.MovieRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataMongoTest
class MovieRepoTest {
//
//    @Mock
//    private MovieRepo movieRepo;
//
//    @InjectMocks
//    private MovieEntity movieEntity;
//
//    @Test
//    void findByTitle_validTitle_shouldReturnMovieEntities() {
//        String title = "Test Movie";
//        when(movieRepo.findByTitle(title)).thenReturn(Arrays.asList(movieEntity));
//
//        List<MovieEntity> result = movieRepo.findByTitle(title);
//
//        assertEquals(Arrays.asList(movieEntity), result);
//        verify(movieRepo, times(1)).findByTitle(title);
//    }
//
////    @Test
////    void findByRatingBetween_validRatingRange_shouldReturnMovieEntities() {
////        String minRating = 3.0;
////        String maxRating = 5.0;
////        when(movieRepo.findByRatingBetween(minRating, maxRating)).thenReturn(Arrays.asList(movieEntity));
////
////        List<MovieEntity> result = movieRepo.findByRatingBetween(minRating, maxRating);
////
////        assertEquals(Arrays.asList(movieEntity), result);
////        verify(movieRepo, times(1)).findByRatingBetween(minRating, maxRating);
////    }
//
//    @Test
//    void findByGenreIn_validGenres_shouldReturnMovieEntities() {
//        List<String> genres = Arrays.asList("Action", "Drama");
//        when(movieRepo.findByGenreIn(genres)).thenReturn(Arrays.asList(movieEntity));
//
//        List<MovieEntity> result = movieRepo.findByGenreIn(genres);
//
//        assertEquals(Arrays.asList(movieEntity), result);
//        verify(movieRepo, times(1)).findByGenreIn(genres);
//    }
}
