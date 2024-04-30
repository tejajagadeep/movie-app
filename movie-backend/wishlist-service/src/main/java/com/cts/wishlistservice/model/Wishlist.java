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
public class Wishlist {
    @Id
    private String username;
    private List<Movie> movies;
}
