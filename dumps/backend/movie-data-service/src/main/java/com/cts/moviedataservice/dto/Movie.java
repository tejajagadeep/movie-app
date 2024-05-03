package com.cts.moviedataservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Movie {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("imdbid")
    private String imdbid;

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

}