package com.cts.wishlistservice.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDto {
    private String username;
    private List<MovieDto> movies;
}
