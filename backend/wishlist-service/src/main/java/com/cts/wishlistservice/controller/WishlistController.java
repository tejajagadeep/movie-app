package com.cts.wishlistservice.controller;

import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getWishlist(@RequestHeader("Authorization") String token, @PathVariable String username){
        return new ResponseEntity<>(wishlistService.getWishlists(token, username), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteWishlist(@RequestHeader("Authorization") String token, @RequestParam String username, @RequestParam String movieId){
        return new ResponseEntity<>(wishlistService.deleteWishlist(token, username, movieId),HttpStatus.OK);
    }

    @PostMapping("/{username}")
    public ResponseEntity<Object> addWishlist(@RequestHeader("Authorization") String token, @PathVariable String username, @RequestBody MovieDto movie){
        return new ResponseEntity<>(wishlistService.addWishlist(token, username,movie),HttpStatus.CREATED);
    }

}
