package com.cts.movieservice.controller;

import com.cts.movieservice.dto.Movie;
import com.cts.movieservice.dto.MovieDetails;
import com.cts.movieservice.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/private/movie")
public class MovieController {

    private final MovieService movieService;


    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "Get Top 100 Movie List from IMDB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found 100 Movies",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class)) }),
            @ApiResponse(responseCode = "404", description = "Movies not found",
                    content = @Content) })
    @GetMapping("/top-100-movies")
    public ResponseEntity<?> topMovies(){
        return new ResponseEntity<>(movieService.topMovies(), HttpStatus.OK);
    }

    @Operation(summary = "Get Movie details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie Details Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieDetails.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie Details not found",
                    content = @Content) })
    @GetMapping("/top-100-movies/{id}")
    public ResponseEntity<?> topMoviesById(@PathVariable String id){
        return new ResponseEntity<>(movieService.topMoviesById(id),HttpStatus.OK);
    }
}
