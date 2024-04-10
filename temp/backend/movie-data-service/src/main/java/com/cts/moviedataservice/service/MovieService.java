package com.cts.moviedataservice.service;


import com.cts.moviedataservice.dto.Movie;

import java.util.List;

public interface MovieService {

    Object topMovies();

    Object topMoviesById(String id);

    Object saveAll(List<Movie> movies);
}
