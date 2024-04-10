package com.cts.wishlistservice.service;


import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;

public interface WishlistService {

    WishlistDto getWishlists(String token, String username);

    WishlistDto deleteWishlist(String token, String username, String id);

    WishlistDto addWishlist(String token, String username, MovieDto movie);
}
