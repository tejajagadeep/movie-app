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
public class MovieDetails {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

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

    @JsonProperty("trailer_embed_link")
    private String trailerEmbedLink;

    @JsonProperty("director")
    private List<String> director;

    @JsonProperty("writers")
    private List<String> writers;
}
