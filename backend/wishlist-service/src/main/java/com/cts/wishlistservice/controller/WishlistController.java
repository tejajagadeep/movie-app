package com.cts.wishlistservice.controller;

import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;
import com.cts.wishlistservice.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Get Wishlist of User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Wishlist of user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Wishlist not found",
                    content = @Content) })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{username}")
    public ResponseEntity<Object> getWishlist(@RequestHeader("Authorization") String token, @PathVariable String username){
        return new ResponseEntity<>(wishlistService.getWishlists(token, username), HttpStatus.OK);
    }

    @Operation(summary = "Delete Movie from Favorite List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Bill Board 100 Playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie not delete",
                    content = @Content) })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("")
    public ResponseEntity<Object> deleteWishlist(@RequestHeader("Authorization") String token, @RequestParam String username, @RequestParam String movieId){
        return new ResponseEntity<>(wishlistService.deleteWishlist(token, username, movieId),HttpStatus.OK);
    }

    @Operation(summary = "Save favorite movie to wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saved movie to wishlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Move not saved",
                    content = @Content) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{username}")
    public ResponseEntity<Object> addWishlist(@RequestHeader("Authorization") String token, @PathVariable String username, @RequestBody MovieDto movie){
        return new ResponseEntity<>(wishlistService.addWishlist(token, username,movie),HttpStatus.CREATED);
    }

}
