package com.cts.wishlistservice.model;

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
    private String imdbid;

    private String description;

    private String title;

    private String image;

    private List<String> genre;

    private float rating;

    private int year;

    private String imdbLink;

}