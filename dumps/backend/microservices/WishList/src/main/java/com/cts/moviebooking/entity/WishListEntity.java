package com.cts.moviebooking.entity;

import com.cts.moviebooking.dto.MovieDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "wishlist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WishListEntity {

    @Id
    private String id;

    private String username;

    private List<MovieDto> movies;

}
