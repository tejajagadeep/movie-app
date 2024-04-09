package com.cts.moviebooking.controller;

import com.cts.moviebooking.dto.MovieDto;
import com.cts.moviebooking.exception.WishlistException;
import com.cts.moviebooking.service.WishlistMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/wishlist/movie")
public class WishListMovieController {

    private static final Logger logger = LoggerFactory.getLogger(WishListMovieController.class);

    @Autowired
    private WishlistMovieService wishlistMovieService;

    @GetMapping("/top100")
    public ResponseEntity<?> getTop100Movies() {
        try {
            List<MovieDto> movies = wishlistMovieService.getTop100Movies();
            logger.info("Retrieved top 100 movies");
            return ResponseEntity.ok(movies);
        } catch (WishlistException e) {
            logger.error("Error getting top 100 movies", e);
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/byTitle")
    public ResponseEntity<?> getMoviesByTitle(@RequestParam String title) {
        try {
            List<MovieDto> movies = wishlistMovieService.getMoviesByTitle(title);
            if (movies.isEmpty()) {
                logger.info("No movies found by title: {}", title);
                return ResponseEntity.status(404).body("No movies found by title: " + title);
            } else {
                logger.info("Retrieved movies by title: {}", title);
                return ResponseEntity.ok(movies);
            }
        } catch (WishlistException e) {
            logger.error("Error getting movies by title: {}", title, e);
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/byRating")
    public ResponseEntity<?> getMoviesByRating(@RequestParam double minRating, @RequestParam double maxRating) {
        try {
            List<MovieDto> movies = wishlistMovieService.getMoviesByRating(minRating, maxRating);
            logger.info("Retrieved movies by rating range: {} - {}", minRating, maxRating);
            return ResponseEntity.ok(movies);
        } catch (WishlistException e) {
            logger.error("Error getting movies by rating range: {} - {}", minRating, maxRating, e);
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/byGenres")
    public ResponseEntity<?> getMoviesByGenres(@RequestParam List<String> genres) {
        try {
            List<MovieDto> movies = wishlistMovieService.getMoviesByGenres(genres);
            logger.info("Retrieved movies by genres: {}", genres);
            return ResponseEntity.ok(movies);
        } catch (WishlistException e) {
            logger.error("Error getting movies by genres: {}", genres, e);
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/topdb100")
    public List<MovieDto> getTopdb100Movies() {
        return wishlistMovieService.getTopdb100Movie();
    }
}
