package com.cts.movieservice.service;


import com.cts.movieservice.dto.Movie;
import com.cts.movieservice.dto.MovieDetails;

import java.util.List;

public interface MovieService {

    List<Movie> topMovies();

    MovieDetails topMoviesById(String id);

}
