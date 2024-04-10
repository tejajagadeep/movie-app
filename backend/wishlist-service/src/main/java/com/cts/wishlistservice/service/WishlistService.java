package com.cts.wishlistservice.service;


import com.cts.wishlistservice.dto.WishlistDto;
import com.cts.wishlistservice.model.Movie;

public interface WishlistService {

    WishlistDto getWishlists(String username);

    WishlistDto deleteWishlist(String username, String id);

    WishlistDto addWishlist(String username, Movie movie);
}
