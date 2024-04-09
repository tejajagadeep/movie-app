package com.cts.moviebooking.controller;

import com.cts.moviebooking.dto.MovieDto;
import com.cts.moviebooking.entity.WishListEntity;
import com.cts.moviebooking.exception.NotFoundException;
import com.cts.moviebooking.exception.WishlistException;
import com.cts.moviebooking.service.WishListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistControllerTest {

    @Mock
    private WishListService wishListService;

    @InjectMocks
    private WishlistController wishlistController;

    @Test
    void getWishlistByUsername_existingUsername_shouldReturnOk() throws NotFoundException, WishlistException {
        String username = "john_doe";
        WishListEntity wishListEntity = new WishListEntity();
        when(wishListService.getWishlistByUsername(username)).thenReturn(Optional.of(wishListEntity));

        ResponseEntity<?> responseEntity = wishlistController.getWishlistByUsername(username);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getWishlistByUsername_nonExistingUsername_shouldReturnNotFound() throws NotFoundException, WishlistException {
        String username = "nonexistent_user";
        when(wishListService.getWishlistByUsername(username)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = wishlistController.getWishlistByUsername(username);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void addToWishlist_validData_shouldReturnOk() throws NotFoundException, WishlistException {
        String username = "john_doe";
        MovieDto movieDto = new MovieDto();
        WishListEntity wishListEntity = new WishListEntity();
        when(wishListService.addToWishlist(username, movieDto)).thenReturn(wishListEntity);

        ResponseEntity<?> responseEntity = wishlistController.addToWishlist(username, movieDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void removeFromWishlist_existingData_shouldReturnOk() throws NotFoundException, WishlistException {
        String username = "john_doe";
        MovieDto movieDto = new MovieDto();
        WishListEntity wishListEntity = new WishListEntity();
        when(wishListService.removeFromWishlist(username, movieDto)).thenReturn(wishListEntity);

        ResponseEntity<?> responseEntity = wishlistController.removeFromWishlist(username, movieDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void clearWishlist_existingUsername_shouldReturnOk() throws NotFoundException, WishlistException {
        String username = "john_doe";

        ResponseEntity<?> responseEntity = wishlistController.clearWishlist(username);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());


        assertNotNull(responseEntity.getBody());
//        assertEquals("Wishlist cleared successfully.", responseEntity.getBody().get("message"));
    }
}
