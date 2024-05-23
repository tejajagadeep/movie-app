package com.cts.movieservice.service;

import com.cts.movieservice.dto.Movie;
import com.cts.movieservice.dto.MovieDetails;
import com.cts.movieservice.dto.MovieResponse;
import com.cts.movieservice.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Log4j2
public class MovieServiceImpl implements MovieService{

    @Value("${rapid.api.key}")
    private String key;

    @Value("${rapid.api.host}")
    private String host;

    @Value(("${base.url}"))
    private String baseUrl;


    private final RestTemplate restTemplate;

    public MovieServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Observed(name = "top.movies")
    @CircuitBreaker(name = "MovieServiceImpl", fallbackMethod = "getAllMoviesBreakCircuit")
    public MovieResponse topMovies(){
        List<Movie> movies =  restTemplate.exchange(response(""), new ParameterizedTypeReference<List<Movie>>() {}).getBody();

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setStatus(true);
        movieResponse.setMessage("Successful");
        movieResponse.setData(movies);

        return movieResponse;
    }


    @Override
    @Observed(name = "top.movies.page.nation")
    @CircuitBreaker(name = "MovieServiceImpl", fallbackMethod = "topMoviesPageNationBreakCircuit")
    public MovieResponse topMoviesPageNation(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        // Fetch movies using offset and pageSize
        List<Movie> movies = restTemplate.exchange(response("?page=" + offset + "&size=" + pageSize),
                new ParameterizedTypeReference<List<Movie>>() {}).getBody();

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setStatus(true);
        movieResponse.setMessage("Successful");
        movieResponse.setData(movies);

        return movieResponse;
    }

    @Override
    @CircuitBreaker(name = "MovieServiceImpl", fallbackMethod = "getMoviesSearchBreakCircuit")
    @Observed(name = "top.movies.search")
    public MovieResponse topMoviesSearch(String search) {
        ResponseEntity<List<Movie>> responseEntity = restTemplate.exchange(response(""), new ParameterizedTypeReference<>() {});

        // Extract the body from the movieResponse entity
        List<Movie> movies = Optional.ofNullable(responseEntity.getBody()).orElse(Collections.emptyList());

        // Create a new MovieResponse object
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setStatus(true);
        movieResponse.setMessage("Successful");
        movieResponse.setData(movies.stream().filter(s -> {
            String title = s.getTitle();
            return title != null && title.toLowerCase().contains(search.toLowerCase());
        }).toList());

        if (movieResponse.getData().isEmpty()) {
            throw new ResourceNotFoundException("Not Found Search Result");
        }

        return movieResponse;
    }

    @Override
    @Observed(name = "top.movies.by.genre")
    @CircuitBreaker(name = "MovieServiceImpl", fallbackMethod = "topMoviesByGenreBreakCircuit")
    public MovieResponse topMoviesByGenre(String genre) {
        ResponseEntity<List<Movie>> responseEntity = restTemplate.exchange(response(""), new ParameterizedTypeReference<>() {});

        // Extract the body from the movieResponse entity
        List<Movie> movies = Optional.ofNullable(responseEntity.getBody()).orElse(Collections.emptyList());

        // Create a new MovieResponse object
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setStatus(true);
        movieResponse.setMessage("Successful");
        movieResponse.setData(
                movies.stream()
                        .filter(s -> {
                            List<String> genres = s.getGenre();
                            return genres != null && genres.stream()
                                    .anyMatch(g -> g != null && g.equalsIgnoreCase(genre));
                        })
                        .toList()
        );
        if (movieResponse.getData().isEmpty()) {
            throw new ResourceNotFoundException("Not Found Search Result");
        }
        return movieResponse;
    }


    @Override
    @Observed(name = "top.movies.by.id")
    @CircuitBreaker(name = "MovieServiceImpl", fallbackMethod = "getMovieDetailsById")
    public MovieDetails topMoviesById(String id) {
        return restTemplate.exchange(response(id), MovieDetails.class).getBody();
    }


    private RequestEntity<Void> response(String query){
        URI uri = UriComponentsBuilder.fromUriString(baseUrl+query)
                .build()
                .toUri();
        return RequestEntity.get(uri).headers(httpHeaders()).build();
    }

    private HttpHeaders httpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", key);
        headers.set("X-RapidAPI-Host", host);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    public MovieResponse topMoviesPageNationBreakCircuit(int page, int pageSize, Throwable throwable) throws IOException {
        Exception e = new IOException();
        log.error("Fallback method called for topMoviesPageNationBreakCircuit with error {}", throwable.getMessage());

        // Get the full list of movies from the fallback method
        MovieResponse movieResponse = getAllMoviesBreakCircuit(e);

        // Apply pagination
        List<Movie> movies = movieResponse.getData();
        int totalMovies = movies.size();

        int startIdx = (page - 1) * pageSize;
        int endIdx = Math.min(startIdx + pageSize, totalMovies);

        List<Movie> pagedMovies = movies.subList(startIdx, endIdx);

        // Update movieResponse data with paginated movies
        movieResponse.setData(pagedMovies);

        return movieResponse;
    }


    public MovieResponse getMoviesSearchBreakCircuit(String search, Throwable throwable) throws IOException {
        Exception e = new IOException();
        log.error("fall back method called for getMoviesSearchBreakCircuit with error {}", throwable.getMessage());
        MovieResponse movieResponse = getAllMoviesBreakCircuit(e);
        movieResponse.setData(movieResponse.getData().stream().filter(s->s.getTitle().toLowerCase().contains(search.toLowerCase())).toList());
        if (movieResponse.getData().isEmpty()) {
            throw new ResourceNotFoundException("Not Found Search Result");
        }
        return movieResponse;
    }


    public MovieResponse topMoviesByGenreBreakCircuit(String genre, Throwable throwable) throws IOException {
        Exception e = new IOException();
        log.error("fall back method called for topMoviesByGenre with error {}", throwable.getMessage());
        MovieResponse movieResponse = getAllMoviesBreakCircuit(e);
        Set<String> allGenres = movieResponse.getData().stream()
                .flatMap(movie -> movie.getGenre().stream()) // Flatten the stream of genres
                .filter(Objects::nonNull) // Filter out null genres
                .collect(Collectors.toSet());

        log.info("genre as: {}", allGenres);
        movieResponse.setData(movieResponse.getData().stream().filter(s -> {
            List<String> genres = s.getGenre();
            return genres != null && genres.stream()
                    .anyMatch(g -> g != null && g.equalsIgnoreCase(genre));
        }).toList());
        if (movieResponse.getData().isEmpty()) {
            throw new ResourceNotFoundException("Not Found Search Result");
        }
        return movieResponse;
    }

    public MovieResponse getAllMoviesBreakCircuit(Exception e) throws IOException {
        log.error("fall back method called for getAllMoviesBreakCircuit with error {}", e.getMessage());
        List<Movie> movies = null;

        try {
            // Load the resource stream
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/movie-data.json");
            if (inputStream != null) {
                // Read the JSON data from the input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder jsonString = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonString.append(line);
                }
                reader.close();

                // Convert JSON string to list of Movie objects
                ObjectMapper objectMapper = new ObjectMapper();
                movies = objectMapper.readValue(jsonString.toString(), new TypeReference<>() {});
            } else {
                throw new IOException("Resource not found: static/movie-data.json");
            }
        } catch (IOException io) {
            throw new IOException("Failed to read movie data from file", io);
        }

        // Create and return the movieResponse
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setStatus(false);
        movieResponse.setMessage("Unsuccessful call. Redirecting to dummy data");
        movieResponse.setData(movies);
        return movieResponse;
    }

    private MovieDetails getMovieDetailsById(Exception e){
        log.error("fall back method called with error {}", e.getMessage());
        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setRank(32);
        movieDetails.setTitle("Oppenheimer");
        movieDetails.setRating(8.6F);
        movieDetails.setId("dummy12345");
        movieDetails.setYear(2023);
        movieDetails.setBigImage("https://m.media-amazon.com/images/M/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY@._V1_QL75_UX380_CR0,0,380,562_.jpg");
        movieDetails.setDescription("The story of American scientist, J. Robert Oppenheimer, and his role in the development of the atomic bomb.");
        movieDetails.setTrailerEmbedLink("https://www.youtube.com/embed/uYPbbksJxIg");
        movieDetails.setGenre(List.of("Biography", "Drama", "History"));
        movieDetails.setDirector(List.of("Christopher Nolan"));
        movieDetails.setWriters(List.of("Christopher Nolan", "Kai Bird", "Martin Sherwin"));
        movieDetails.setImdbLink("https://www.imdb.com/title/tt15398776");
        movieDetails.setImdbid("tt0022100");
        return movieDetails;
    }
}
