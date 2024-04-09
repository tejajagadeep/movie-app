package com.cts.moviebooking.entity;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {

    @Id
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
