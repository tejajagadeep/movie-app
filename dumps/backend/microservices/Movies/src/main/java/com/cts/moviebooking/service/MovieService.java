package com.cts.moviebooking.service;

import com.cts.moviebooking.dto.Movie;
import com.cts.moviebooking.entity.MovieEntity;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

@Service
public interface MovieService {

    public List<MovieEntity> getMoviesByGenres(List<String> genres);
    public List<MovieEntity> getMoviesByTitle(String title);
    public List<MovieEntity> convertToMovieEntities(List<Movie> movies);
    public void saveMovies(List<Movie> movies);

    public List<Movie> getTop100Movies();

    public List<MovieEntity> getMoviesByRating(String minRating, String maxRating);


    List<MovieEntity> getTop10Movies();
}
