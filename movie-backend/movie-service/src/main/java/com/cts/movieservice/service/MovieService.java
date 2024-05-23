package com.cts.movieservice.service;


import com.cts.movieservice.dto.MovieDetails;
import com.cts.movieservice.dto.MovieResponse;

public interface MovieService {

    MovieResponse topMovies();

    MovieResponse topMoviesPageNation(int page, int pageSize);

    MovieDetails topMoviesById(String id);

    MovieResponse topMoviesSearch(String search);

    MovieResponse topMoviesByGenre(String genre);

}
