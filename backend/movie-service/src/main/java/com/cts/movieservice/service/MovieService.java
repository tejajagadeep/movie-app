package com.cts.movieservice.service;


import com.cts.movieservice.dto.Movie;
import com.cts.movieservice.dto.MovieDetails;
import com.cts.movieservice.dto.Response;

import java.util.List;

public interface MovieService {

    Response topMovies();

    MovieDetails topMoviesById(String id);

}
