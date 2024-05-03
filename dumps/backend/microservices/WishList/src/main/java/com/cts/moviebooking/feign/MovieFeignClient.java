package com.cts.moviebooking.feign;

import com.cts.moviebooking.dto.MovieDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "MOVIESERVICE")
public interface MovieFeignClient {

    @GetMapping("/api/v1/movies/top100")
    List<MovieDto> getTop100Movies();

    @GetMapping("/api/v1/movies/byTitle")
    List<MovieDto> getMoviesByTitle(@RequestParam String title);

    @GetMapping("/api/v1/movies/byRating")
    List<MovieDto> getMoviesByRating(@RequestParam double minRating, @RequestParam double maxRating);

    @GetMapping("/api/v1/movies/byGenres")
    List<MovieDto> getMoviesByGenres(@RequestParam List<String> genres);

//    @GetMapping("/api/v1/movies/topdb100")
//    Page<MovieDto> getTop100MoviesFromDB(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "rank") String sortBy
//    );

    @GetMapping("/api/v1/movies/topdb100")
    public List<MovieDto> getTop100MoviesFromDB();
}
