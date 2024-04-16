package com.cts.wishlistservice.controller;

import static org.junit.jupiter.api.Assertions.*;

// FILEPATH: /C:/Users/922320/Videos/files/movie-backend/wishlist-service/src/test/java/com/cts/wishlistservice/controller/WishlistControllerTest.java

import com.cts.wishlistservice.controller.WishlistController;
import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;
import com.cts.wishlistservice.filter.JwtService;
import com.cts.wishlistservice.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WishlistControllerTest {

    @Mock
    private WishlistService wishlistService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private WishlistController wishlistController;

    private String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYWdhZGVlcCIsImF1dGhvcml0aWVzIjoiUk9MRV9NRU1CRVIsbWFuYWdlbWVudDpyZWFkLG1hbmFnZW1lbnQ6Y3JlYXRlIiwiaWF0IjoxNzEzMTg1MDU1LCJleHAiOjE3MTMyNzE0NTV9.T9KPOkITFvhzuY6PySraVLLs30i0AnlmpuXUK2N2lCw";
    private String username = "jagadeep";
    private MovieDto movieDto = new MovieDto();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(jwtService.isTokenValid(token.substring(7), username)).thenReturn(true);
    }

    @Test
    void testGetWishlist() {
        when(wishlistService.getWishlists(username)).thenReturn((WishlistDto) Collections.emptyList());

        ResponseEntity<Object> response = wishlistController.getWishlist(token, username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(wishlistService, times(1)).getWishlists(username);
    }

    @Test
    void testGetWishlist_ValidToken_ReturnsWishlistDto() {
        // Arrange
        WishlistDto expectedWishlist = new WishlistDto(); // Instantiate with test data
        when(jwtService.isTokenValid(anyString(), anyString())).thenReturn(true);
        when(wishlistService.getWishlists(anyString())).thenReturn(expectedWishlist);

        // Act
        ResponseEntity<Object> response = wishlistController.getWishlist(token, username);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedWishlist, response.getBody());
    }

    @Test
    void testDeleteWishlist() {
        String id = "1";
        when(wishlistService.deleteWishlist(username, id)).thenReturn(new WishlistDto());

        ResponseEntity<Object> response = wishlistController.deleteWishlist(token, username, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(wishlistService, times(1)).deleteWishlist(username, id);
    }

    @Test
    void testAddWishlist() {
        when(wishlistService.addWishlist(username, movieDto)).thenReturn(new WishlistDto());

        ResponseEntity<Object> response = wishlistController.addWishlist(token, username, movieDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(wishlistService, times(1)).addWishlist(username, movieDto);
    }
}