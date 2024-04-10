package com.cts.wishlistservice.controller;

import com.cts.wishlistservice.model.Movie;
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

    @GetMapping("/favourite/{username}")
    public ResponseEntity<Object> getWishlist(@PathVariable String username){
        return new ResponseEntity<>(wishlistService.getWishlists(username), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> deleteWishlist(@RequestParam String username, @RequestParam String id){
        return new ResponseEntity<>(wishlistService.deleteWishlist(username,id),HttpStatus.OK);
    }

    @PostMapping("/{username}")
    public ResponseEntity<Object> addWishlist(@PathVariable String username, @RequestBody Movie movie){
        return new ResponseEntity<>(wishlistService.addWishlist(username,movie),HttpStatus.OK);
    }

}
