package com.cts.moviebooking.controller;

import com.cts.moviebooking.dto.MovieDto;
import com.cts.moviebooking.entity.WishListEntity;
import com.cts.moviebooking.exception.NotFoundException;
import com.cts.moviebooking.exception.WishlistException;
import com.cts.moviebooking.service.WishListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistController {

    private static final Logger logger = LoggerFactory.getLogger(WishlistController.class);

    private final WishListService wishlistService;

    @Autowired
    public WishlistController(WishListService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getWishlistByUsername(@PathVariable String username) {
        try {
            Optional<WishListEntity> wishlist = wishlistService.getWishlistByUsername(username);
            return wishlist.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (NotFoundException e) {
            logger.error("Error getting wishlist for username: {}", username, e);
            return ResponseEntity.notFound().build();
        } catch (WishlistException e) {
            logger.error("WishlistException while getting wishlist for username: {}", username, e);
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/{username}/add")
    public ResponseEntity<?> addToWishlist(@PathVariable String username, @RequestBody MovieDto movieDto) {
        try {
            WishListEntity wishlist = wishlistService.addToWishlist(username, movieDto);
            logger.info("Added movie to wishlist. Username: {}, Movie: {}", username, movieDto.getTitle());
            return ResponseEntity.ok(wishlist);
        } catch (NotFoundException e) {
            logger.error("Error adding movie to wishlist for username: {}", username, e);
            return ResponseEntity.notFound().build();
        } catch (WishlistException e) {
            logger.error("WishlistException while adding movie to wishlist for username: {}", username, e);
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{username}/remove")
    public ResponseEntity<?> removeFromWishlist(@PathVariable String username, @RequestBody MovieDto movieDto) {
        try {
            WishListEntity wishlist = wishlistService.removeFromWishlist(username, movieDto);
            logger.info("Removed movie from wishlist. Username: {}, Movie: {}", username, movieDto.getTitle());
            return ResponseEntity.ok(wishlist);
        } catch (NotFoundException e) {
            logger.error("Error removing movie from wishlist for username: {}", username, e);
            return ResponseEntity.notFound().build();
        } catch (WishlistException e) {
            logger.error("WishlistException while removing movie from wishlist for username: {}", username, e);
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{username}/clear")
    public ResponseEntity<?> clearWishlist(@PathVariable String username) {
        try {
            wishlistService.clearWishlist(username);
            logger.info("Cleared wishlist for username: {}", username);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Wishlist cleared successfully.");
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            logger.error("Error clearing wishlist for username: {}", username, e);
            return ResponseEntity.notFound().build();
        } catch (WishlistException e) {
            logger.error("WishlistException while clearing wishlist for username: {}", username, e);
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
