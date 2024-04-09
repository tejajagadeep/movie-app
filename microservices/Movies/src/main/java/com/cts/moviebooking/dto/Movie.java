package com.cts.moviebooking.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Movie implements Serializable {
    private String id;

    private int rank;
    private String title;
    private String description;
    private String image;
    private String big_image;
    private String thumbnail;
    private String rating;
    private List<String> genre;
    private int year;
    private String imdbid;
    private String imdb_link;




}
