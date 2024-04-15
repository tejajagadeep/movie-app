package com.cts.wishlistservice.model;

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

    private String id;

    private int rank;

    private String title;

    private String bigImage;

    private List<String> genre;

    private float rating;

    private int year;

    private String imdbLink;

}