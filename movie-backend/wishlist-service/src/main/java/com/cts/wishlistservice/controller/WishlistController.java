package com.cts.wishlistservice.controller;

import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;
import com.cts.wishlistservice.exception.UnAuthorizedException;
import com.cts.wishlistservice.filter.JwtService;
import com.cts.wishlistservice.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/private/wishlist")
@Slf4j
public class WishlistController {

    private final WishlistService wishlistService;
    private final JwtService jwtService;

    @Autowired
    public WishlistController(WishlistService wishlistService, JwtService jwtService) {
        this.wishlistService = wishlistService;
        this.jwtService = jwtService;
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
    @Cacheable(value = "wishlist", key = "#username")
    public ResponseEntity<Object> getWishlist(@RequestHeader("Authorization") String token, @PathVariable String username){
        if (jwtService.isTokenValid(token.substring(7),username)){
            return new ResponseEntity<>(wishlistService.getWishlists(username), HttpStatus.OK);
        }
        throw new UnAuthorizedException("UnAuthorized token access Wishlist");
    }

    @Caching(evict = {
            @CacheEvict(value = "wishlist", key = "#username"),
            @CacheEvict(value = "wishlist", key = "'all'")
    })
    @Operation(summary = "Delete Movie from Favorite List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Bill Board 100 Playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie not delete",
                    content = @Content) })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("")
    public ResponseEntity<Object> deleteWishlist(@RequestHeader("Authorization") String token, @RequestParam String username, @RequestParam String id){
        if (jwtService.isTokenValid(token.substring(7),username)){
            return new ResponseEntity<>(wishlistService.deleteWishlist(username, id),HttpStatus.OK);
        }
        throw new UnAuthorizedException("UnAuthorized token access deleteWishlist");
    }

    @Caching(evict = {
            @CacheEvict(value = "wishlist", key = "#username"),
            @CacheEvict(value = "wishlist", key = "'all'")
    })
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
        log.info(username);
        if (jwtService.isTokenValid(token.substring(7),username)){
            return new ResponseEntity<>(wishlistService.addWishlist(username,movie),HttpStatus.CREATED);
        }
        throw new UnAuthorizedException("UnAuthorized token access add movie");
    }

}
