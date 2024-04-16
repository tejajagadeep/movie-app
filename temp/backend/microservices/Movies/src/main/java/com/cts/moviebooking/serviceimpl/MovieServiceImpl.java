package com.cts.moviebooking.serviceimpl;

import com.cts.moviebooking.dto.Movie;
import com.cts.moviebooking.entity.MovieEntity;
import com.cts.moviebooking.exception.MovieBookingException;
import com.cts.moviebooking.repository.MovieRepo;
import com.cts.moviebooking.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepo movieRepo;

    @Autowired
    public MovieServiceImpl(MovieRepo movieRepository) {
        this.movieRepo = movieRepository;
    }

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    private final String baseUrl = "https://imdb-top-100-movies.p.rapidapi.com/";

    public List<Movie> getTop100Movies() {
        try {
            String endpoint = "";
            URI uri = new URI(baseUrl + endpoint);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Key", rapidApiKey);
            headers.set("X-RapidAPI-Host", "imdb-top-100-movies.p.rapidapi.com");
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            RequestEntity<Void> requestEntity = RequestEntity.get(uri).headers(headers).build();

            ResponseEntity<Movie[]> responseEntity = new RestTemplate().exchange(requestEntity, Movie[].class);

            List<Movie> movies = Arrays.asList(responseEntity.getBody());
            saveMovies(movies);
            for (Movie movie : movies) {
                System.out.println("IMDB ID: " + movie.getImdbid());
            }
            return movies;
        } catch (URISyntaxException ex) {
            log.error("Error constructing URI: {}", ex.getMessage());
            throw new MovieBookingException("Error while fetching top 100 movies. Please try again.");
        } catch (Exception ex) {
            log.error("Error while fetching top 100 movies: {}", ex.getMessage());
            throw new MovieBookingException("Error while fetching top 100 movies. Please try again.");
        }
    }

    public void saveMovies(List<Movie> movies) {
        try {
            log.info("Saving movies to the database");
            movieRepo.deleteAll();
            List<MovieEntity> movieEntities = convertToMovieEntities(movies);
            movieRepo.saveAll(movieEntities);
        } catch (Exception ex) {
            log.error("Error while saving movies to the database: {}", ex.getMessage());
            throw new MovieBookingException("Error while saving movies to the database. Please try again.");
        }
    }

    public List<MovieEntity> convertToMovieEntities(List<Movie> movies) {
        try {
            log.info("Converting movies to movie entities");
            return movies.stream()
                    .map(movie -> {
                        MovieEntity movieEntity = new MovieEntity();

                        movieEntity.setRank(movie.getRank());
                        movieEntity.setTitle(movie.getTitle());
                        movieEntity.setDescription(movie.getDescription());
                        movieEntity.setImage(movie.getImage());
                        movieEntity.setBigImage(movie.getBig_image());
                        movieEntity.setRating(movie.getRating());
                        movieEntity.setThumbnail(movie.getThumbnail());
                        movieEntity.setId(movie.getId());
                        movieEntity.setYear(movie.getYear());
                        movieEntity.setImdbId(movie.getImdbid());
                        movieEntity.setImdbLink(movie.getImdb_link());

                        List<String> genreList = new ArrayList<>();
                        if (movie.getGenre() != null) {
                            if (movie.getGenre() instanceof List) {
                                // Assuming movie.getGenre() is a List<String>
                                genreList.addAll((List<String>) movie.getGenre());
                            } else {
                                // If it's a single string, add it to the list
                                genreList.add(movie.getGenre().toString());
                            }
                        }
                        String genreAsString = String.join(",", genreList);
                        movieEntity.setGenre(genreList);


                        return movieEntity;
                    })
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Error while converting movies to entities: {}", ex.getMessage());
            throw new MovieBookingException("Error while converting movies to entities. Please try again.");
        }
    }

    public List<MovieEntity> getMoviesByTitle(String title) {
        try {
            log.info("Fetching movies by title: {}", title);
            return movieRepo.findByTitle(title);
        } catch (Exception ex) {
            log.error("Error while fetching movies by title: {}", ex.getMessage());
            throw new MovieBookingException("Error while fetching movies by title. Please try again.");
        }
    }

    public List<MovieEntity> getMoviesByRating(String minRating, String maxRating) {
        try {
            log.info("Fetching movies by rating between {} and {}", minRating, maxRating);
            return movieRepo.findByRatingBetween(minRating, maxRating);
        } catch (Exception ex) {
            log.error("Error while fetching movies by rating: {}", ex.getMessage());
            throw new MovieBookingException("Error while fetching movies by rating. Please try again.");
        }
    }

    @Override
    public List<MovieEntity> getTop10Movies() {
        List<MovieEntity> allMovies = movieRepo.findAll();

        if (allMovies == null || allMovies.isEmpty()) {
            return null;
        }

        // Sort the movies based on rank (assuming rank is an integer field)
        List<MovieEntity> sortedMovies = allMovies.stream()
                .sorted(Comparator.comparingInt(MovieEntity::getRank))
                .collect(Collectors.toList());

        // Get the top 10 movies
        List<MovieEntity> top10Movies = sortedMovies.stream()
                .limit(10)
                .collect(Collectors.toList());

        return top10Movies;
    }


    public List<MovieEntity> getMoviesByGenres(List<String> genres) {
        try {
            log.info("Fetching movies by genres: {}", genres);
            return movieRepo.findByGenreIn(genres);
        } catch (Exception ex) {
            log.error("Error while fetching movies by genres: {}", ex.getMessage());
            throw new MovieBookingException("Error while fetching movies by genres. Please try again.");
        }
    }



}
