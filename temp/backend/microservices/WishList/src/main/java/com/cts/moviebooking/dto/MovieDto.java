package com.cts.moviebooking.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDto implements Serializable {
    private String id;

    private int rank;
    private String title;
    private String description;
    private String image;
    private String bigImage;
    private String thumbnail;
    private String rating;
    private List<String> genre;
    private int year;
    private String imdbId;
    private String imdbLink;



}
