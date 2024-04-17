package com.cts.movieservice.service;

import com.cts.movieservice.dto.Movie;
import com.cts.movieservice.dto.MovieDetails;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.micrometer.observation.annotation.Observed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MovieServiceImpl implements MovieService{

    @Value("${rapid.api.key}")
    private String key;

    @Value("${rapid.api.host}")
    private String host;

    @Value(("${base.url}"))
    private String baseUrl;

    private final ModelMapper modelMapper;
    private final ResourceLoader resourceLoader;

    private final RestTemplate restTemplate;

    public MovieServiceImpl(ModelMapper modelMapper, ResourceLoader resourceLoader, RestTemplate restTemplate) {
        this.modelMapper = modelMapper;
        this.resourceLoader = resourceLoader;
        this.restTemplate = restTemplate;
    }

    @Override
    @Observed(name = "top.movies")
    @CircuitBreaker(name = "MovieServiceImpl", fallbackMethod = "getAllMoviesBreakCircuit")
    public Object topMovies(){
        return restTemplate.exchange(response(""), Movie[].class).getBody();
    }

    @Override
    @Observed(name = "top.movies.by.id")
    public Object topMoviesById(String id) {
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
        headers.set("X-RapidAPI-Key", key+"dgwergh");
        headers.set("X-RapidAPI-Host", host);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private Movie[] getAllMoviesBreakCircuit(Exception e) throws IOException {
        Movie[] movies = null;
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

            movies = modelMapper.map(jsonString, Movie[].class);


        } catch (IOException io) {
            throw new IOException();
        }

        return movies;
    }
}
