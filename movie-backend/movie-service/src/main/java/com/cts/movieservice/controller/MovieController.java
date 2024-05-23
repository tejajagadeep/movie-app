package com.cts.movieservice.controller;


import com.cts.movieservice.dto.MovieDetails;
import com.cts.movieservice.dto.MovieResponse;
import com.cts.movieservice.exception.ErrorResponse;
import com.cts.movieservice.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/public/movie")
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
                            schema = @Schema(implementation = MovieResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Movies not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping("/top-100-movies")
    public ResponseEntity<Object> topMovies(){
        return new ResponseEntity<>(movieService.topMovies(), HttpStatus.OK);
    }

    @Operation(summary = "Search from Top 100 Movie List from IMDB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Movies",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Movies not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping("/top-100-movies/search/{search}")
    public ResponseEntity<Object> topMoviesSearch(@PathVariable String search){
        return new ResponseEntity<>(movieService.topMoviesSearch(search), HttpStatus.OK);
    }

    @Operation(summary = "Filter from Top 100 Movie List from IMDB by genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Movies",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Movies not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping("/top-100-movies/filter-genre/{genre}")
    public ResponseEntity<Object> topMoviesByGenre(@PathVariable String genre){
        return new ResponseEntity<>(movieService.topMoviesByGenre(genre), HttpStatus.OK);
    }

    @Operation(summary = "Get Movie details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie Details Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieDetails.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie Details not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping("/top-100-movies/{id}")
    public ResponseEntity<Object> topMoviesById(@PathVariable String id){
        return new ResponseEntity<>(movieService.topMoviesById(id),HttpStatus.OK);
    }
}
