package com.cts.wishlistservice.controller;

import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;
import com.cts.wishlistservice.exception.ErrorResponse;
import com.cts.wishlistservice.exception.UnAuthorizedException;
import com.cts.wishlistservice.filter.JwtService;
import com.cts.wishlistservice.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/private/wishlist")
@Log4j2
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
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) ) })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{username}")
    public ResponseEntity<Object> getWishlist(@Parameter(hidden = true) @RequestHeader("Authorization") String token, @PathVariable String username){
        if (jwtService.isTokenValid(token,username)){
            return new ResponseEntity<>(wishlistService.getWishlists(username), HttpStatus.OK);
        }
        throw new UnAuthorizedException("Unauthorized token access Wishlist");
    }

    @Operation(summary = "Delete Movie from Favorite List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Bill Board 100 Playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Movie not delete",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) ) })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("")
    public ResponseEntity<Object> deleteWishlist(@Parameter(hidden = true) @RequestHeader("Authorization") String token, @RequestParam String username, @RequestParam String id){
        if (jwtService.isTokenValid(token,username)){
            return new ResponseEntity<>(wishlistService.deleteWishlist(username, id),HttpStatus.OK);
        }
        throw new UnAuthorizedException("Unauthorized token access deleteWishlist");
    }

    @Operation(summary = "Save favorite movie to wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saved movie to wishlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Move not saved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) ) })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{username}")
    public ResponseEntity<Object> addWishlist(@Parameter(hidden = true) @RequestHeader("Authorization") String token, @PathVariable String username, @RequestBody MovieDto movie){
        if (jwtService.isTokenValid(token,username)){
            return new ResponseEntity<>(wishlistService.addWishlist(username,movie),HttpStatus.CREATED);
        }
        throw new UnAuthorizedException("Unauthorized token access add movie");
    }

}
