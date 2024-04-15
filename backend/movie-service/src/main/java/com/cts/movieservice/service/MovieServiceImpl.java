package com.cts.movieservice.service;

import com.cts.movieservice.dto.Movie;
import com.cts.movieservice.dto.MovieDetails;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    private final RestTemplate restTemplate;

    public MovieServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Observed(name = "top.movies")
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
        headers.set("X-RapidAPI-Key", key);
        headers.set("X-RapidAPI-Host", host);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}
