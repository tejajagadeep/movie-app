package com.cts.movieservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @JsonProperty("id")
    private String id;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("title")
    private String title;

    @JsonProperty("big_image")
    private String bigImage;

    @JsonProperty("genre")
    private List<String> genre;

    @JsonProperty("rating")
    private float rating;

    @JsonProperty("year")
    private int year;

    @JsonProperty("imdb_link")
    private String imdbLink;

    @JsonProperty("imdbid")
    private String imdbid;
}