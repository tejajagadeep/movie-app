package com.cts.wishlistservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {

    @JsonProperty("imdbid")
    private String imdbid;

    @JsonProperty("title")
    private String title;

    @JsonProperty("big_image")
    private String bigImage;

    @JsonProperty("description")
    private String description;

    @JsonProperty("genre")
    private List<String> genre;

    @JsonProperty("rating")
    private float rating;

    @JsonProperty("year")
    private int year;

    @JsonProperty("imdb_link")
    private String imdbLink;

}