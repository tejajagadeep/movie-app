package com.cts.wishlistservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@ToString
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