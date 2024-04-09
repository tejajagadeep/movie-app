package com.cts.moviebooking.service;

import com.cts.moviebooking.dto.MovieDto;
import com.cts.moviebooking.entity.WishListEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface WishListService {

    public void clearWishlist(String username);
    public WishListEntity removeFromWishlist(String username, MovieDto movieDto);
    public WishListEntity addToWishlist(String username, MovieDto movieDto);
    public Optional<WishListEntity> getWishlistByUsername(String username);
}
