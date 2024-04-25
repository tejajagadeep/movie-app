package com.cts.movieservice.service;

import com.cts.movieservice.dto.Movie;
import com.cts.movieservice.dto.MovieDetails;
import com.cts.movieservice.dto.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
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
    public Response topMovies(){
        List<Movie> movies =  restTemplate.exchange(response(""), new ParameterizedTypeReference<List<Movie>>() {}).getBody();

        Response response = new Response();
        response.setStatus(true);
        response.setMessage("Successful");
        response.setData(movies);

        return response;
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

    private Response getAllMoviesBreakCircuit(Exception e) throws IOException {
        log.error("fall back method called for getAllMoviesBreakCircuit with error {}", e.getMessage());
        List<Movie> movies = new ArrayList<>();
        try {
            // Specify the path to your text file containing JSON data
            String path = "movie-data.txt";
            ClassPathResource resource = new ClassPathResource(path);
            File file = resource.getFile();
            StringBuilder jsonString = new StringBuilder();
            java.util.Scanner scanner = new java.util.Scanner(file);
            while (scanner.hasNextLine()) {
                jsonString.append(scanner.nextLine());
            }
            scanner.close();
            ObjectMapper objectMapper = new ObjectMapper();
            movies = objectMapper.readValue(jsonString.toString(),new TypeReference<>() {});


        } catch (IOException io) {
            throw new IOException();
        }

        Response response = new Response();
        response.setStatus(false);
        response.setMessage("Unsuccessful");
        response.setData(movies);

        return response;
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
