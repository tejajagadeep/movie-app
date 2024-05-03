package com.cts.moviebooking.serviceimpl;

import com.cts.moviebooking.dto.MovieDto;
import com.cts.moviebooking.feign.MovieFeignClient;
import com.cts.moviebooking.service.WishlistMovieService;
import com.cts.moviebooking.util.MovieDtoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListMovieServiceImpl implements WishlistMovieService {

    private static final Logger logger = LoggerFactory.getLogger(WishListMovieServiceImpl.class);

    private final MovieFeignClient movieFeignClient;

    @Autowired
    public WishListMovieServiceImpl(MovieFeignClient movieFeignClient) {
        this.movieFeignClient = movieFeignClient;
    }

    public List<MovieDto> getTop100Movies() {
        logger.info("Fetching top 100 movies");
        List<MovieDto> response = movieFeignClient.getTop100Movies();

        for (int i = 0; i <= 5; i++) {
            response.stream()
                    .filter(movie -> "The Shawshank Redemption".equals(movie.getTitle()))
                    .forEach(movie -> logger.info("Found movie by title: {}", movie.getTitle()));
        }

        return response;
    }

    public List<MovieDto> getMoviesByTitle(String title) {
        logger.info("Fetching movies by title: {}", title);
        List<MovieDto> movie = movieFeignClient.getMoviesByTitle(title);
        return movie;
    }

    @Override
    public List<MovieDto> getTopdb100Movie() {
       return movieFeignClient.getTop100MoviesFromDB();
    }

    public List<MovieDto> getMoviesByRating(double minRating, double maxRating) {
        logger.info("Fetching movies by rating range: {} - {}", minRating, maxRating);
        List<MovieDto> movies = movieFeignClient.getMoviesByRating(minRating, maxRating);
        List<MovieDto> uniqueMovies = MovieDtoUtils.removeDuplicatesByTitle(movies);
        return uniqueMovies;
    }

    public List<MovieDto> getMoviesByGenres(List<String> genres) {
        logger.info("Fetching movies by genres: {}", genres);
        List<MovieDto> movies = movieFeignClient.getMoviesByGenres(genres);
        return movies;
    }

//    public Page<MovieDto> getTop100Movies(int page, int size, String sortBy) {
//        return movieFeignClient.getTop100MoviesFromDB(page, size, sortBy);
//    }
}
