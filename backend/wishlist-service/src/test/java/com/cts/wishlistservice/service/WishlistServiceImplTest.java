package com.cts.wishlistservice.service;

import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;
import com.cts.wishlistservice.exception.ResourceNotFoundException;
import com.cts.wishlistservice.model.Movie;
import com.cts.wishlistservice.model.Wishlist;
import com.cts.wishlistservice.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WishlistServiceImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testGetWishlists_Success() throws ResourceNotFoundException {
        String username = "testUser";
        Wishlist wishlist = new Wishlist(username, List.of());
        Optional<Wishlist> optionalWishlist = Optional.of(wishlist);

        // Mock repository behavior
        when(wishlistRepository.findById(username)).thenReturn(optionalWishlist);
        when(modelMapper.map(wishlist, WishlistDto.class)).thenReturn(new WishlistDto());

        // Call the service method
        WishlistDto wishlistDto = wishlistService.getWishlists(username);

        // Verify interactions
        verify(wishlistRepository).findById(username);
        verify(modelMapper).map(wishlist, WishlistDto.class);

        // Assert response
        assertNotNull(wishlistDto);
    }

    @Test
    public void testGetWishlists_UsernameNotFound() throws Exception {
        String username = "testUser";

        // Mock repository behavior
        when(wishlistRepository.findById(username)).thenReturn(Optional.empty());

        // Expected exception
        expectedException(ResourceNotFoundException.class);
        expectedExceptionMessage("Username not found.");

        // Call the service method
        assertThrows(ResourceNotFoundException.class,()->wishlistService.getWishlists(username));

    }

    private void expectedExceptionMessage(String s) {
    }

    private void expectedException(Class<ResourceNotFoundException> resourceNotFoundExceptionClass) {
    }

    @Test
    public void testDeleteWishlist_UsernameNotFound() throws Exception {
        String username = "testUser";
        String movieId = "tt1234567";

        // Mock repository behavior
        when(wishlistRepository.findById(username)).thenReturn(Optional.empty());

        // Expected exception
        expectedException(ResourceNotFoundException.class);
        expectedExceptionMessage("Username " + username + " not found.");

        // Call the service method
        assertThrows(ResourceNotFoundException.class,()->wishlistService.deleteWishlist(username, movieId));
    }

    @Test
    public void testAddWishlist_NewUser() throws Exception {
        String username = "testUser";
        MovieDto movieDto = new MovieDto();

        // Mock repository behavior
        when(wishlistRepository.findById(username)).thenReturn(Optional.empty());
        when(modelMapper.map(movieDto, Movie.class)).thenReturn(new Movie());
//        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(new Wishlist());

        // Call the service method
        WishlistDto wishlistDto = wishlistService.addWishlist(username, movieDto);

        // Verify interactions
        verify(wishlistRepository).findById(username);
        verify(modelMapper).map(movieDto, Movie.class);
//        verify(wishlistRepository).save(any(Wishlist.class));

        // Assert response
        System.out.println(wishlistDto);
        assertNotNull(wishlistDto);
    }

    @Test
    public void testAddWishlist_ExistingUser() throws Exception {
        String username = "testUser";
        MovieDto movieDto = new MovieDto();
        Wishlist wishlist = new Wishlist(username, List.of());
        Movie movie = new Movie();

        // Mock repository behavior
        when(wishlistRepository.findById(username)).thenReturn(Optional.of(wishlist));
        when(modelMapper.map(movieDto, Movie.class)).thenReturn(movie);
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);

        // Call the service method
        WishlistDto wishlistDto = wishlistService.addWishlist(username, movieDto);
    }
    }
