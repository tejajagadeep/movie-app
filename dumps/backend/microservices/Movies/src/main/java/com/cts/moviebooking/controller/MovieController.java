package com.cts.moviebooking.controller;

import com.cts.moviebooking.dto.Movie;
import com.cts.moviebooking.entity.MovieEntity;
import com.cts.moviebooking.repository.MovieRepo;
import com.cts.moviebooking.service.MovieService;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    @Autowired
    private MovieRepo movieRepo;

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/top100")
    public List<Movie> getTop100Movies() {
        log.info("Fetching top 100 movies");
        return movieService.getTop100Movies();
    }

//    @GetMapping("/topdb100")
//    public ResponseEntity<Page<MovieEntity>> getTop100MoviesFromDB(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "rank") String sortBy
//    ) {
//        log.info("Fetching top 100 movies");
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
//        Page<MovieEntity> moviePage = movieRepo.findAll(pageable);
//
//        return ResponseEntity.ok(new PageImpl<>(moviePage.getContent(), pageable, moviePage.getTotalElements()));
//    }

    @GetMapping("/topdb100")
    public List<MovieEntity> getTop100MoviesFromDB()
    {

        List<MovieEntity> movieDetails = movieRepo.findAll();
        log.info("Retrieved top 10 movies");
        return movieDetails;
    }



    @GetMapping("/top10")
    public List<MovieEntity> getTop10Movies() {
            List<MovieEntity> movies = movieService.getTop10Movies();
            log.info("Retrieved top 10 movies");
            return movies;
    }

    @GetMapping("/byTitle")
    public List<MovieEntity> getMoviesByTitle(@RequestParam String title) {
        log.info("Fetching movies by title: {}", title);
        return movieService.getMoviesByTitle(title);
    }

    @GetMapping("/byRating")
    public List<MovieEntity> getMoviesByRating(@RequestParam String minRating, @RequestParam String maxRating) {
        log.info("Fetching movies by rating between {} and {}", minRating, maxRating);
        return movieService.getMoviesByRating(minRating, maxRating);
    }

    @GetMapping("/byGenres")
    public List<MovieEntity> getMoviesByGenres(@RequestParam List<String> genres) {
        log.info("Fetching movies by genres: {}", genres);
        return movieService.getMoviesByGenres(genres);
    }
}
