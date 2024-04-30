package com.cts.wishlistservice.service;


import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;

public interface WishlistService {

    WishlistDto getWishlists(String username);

    WishlistDto deleteWishlist(String username, String id);

    WishlistDto addWishlist(String username, MovieDto movie);
}
