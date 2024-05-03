package com.cts.moviebooking.repository;

import com.cts.moviebooking.entity.MovieEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepo extends MongoRepository<MovieEntity, String> {

    List<MovieEntity> findByTitle(String title);

    List<MovieEntity> findByRatingBetween(String minRating, String maxRating);

    List<MovieEntity> findByGenreIn(List<String> genres);
}
