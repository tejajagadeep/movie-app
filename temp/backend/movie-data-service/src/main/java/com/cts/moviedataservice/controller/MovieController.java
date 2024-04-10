package com.cts.moviedataservice.controller;

import com.cts.moviedataservice.dto.Movie;
import com.cts.moviedataservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/movie")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/top-100-movies")
    public ResponseEntity<Object> topMovies(){
        return new ResponseEntity<>(movieService.topMovies(), HttpStatus.OK);
    }

    @GetMapping("/top-100-movies/{id}")
    public ResponseEntity<Object> topMoviesById(@PathVariable String id){
        return new ResponseEntity<>(movieService.topMoviesById(id),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> addMovie(@RequestBody List<Movie> movies){
        return new ResponseEntity<>(movieService.saveAll(movies), HttpStatus.CREATED);
    }
}
