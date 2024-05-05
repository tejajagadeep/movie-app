package com.cts.wishlistservice.service;

import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;
import com.cts.wishlistservice.exception.ResourceNotFoundException;
import com.cts.wishlistservice.model.Movie;
import com.cts.wishlistservice.model.Wishlist;
import com.cts.wishlistservice.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WishlistServiceImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWishlists_UserExists_ReturnsWishlistDto() {
        // Given
        String username = "user1";
        Wishlist wishlist = new Wishlist();
        wishlist.setUsername(username);
        WishlistDto expectedWishlistDto = new WishlistDto();
        expectedWishlistDto.setUsername(username);
        when(wishlistRepository.findById(username)).thenReturn(Optional.of(wishlist));
        when(modelMapper.map(wishlist, WishlistDto.class)).thenReturn(expectedWishlistDto);

        // When
        WishlistDto result = wishlistService.getWishlists(username);

        // Then
        assertEquals(expectedWishlistDto, result);
    }

    @Test
    void getWishlists_UserDoesNotExist_ThrowsResourceNotFoundException() {
        // Given
        String username = "nonExistingUser";
        when(wishlistRepository.findById(username)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> {
            wishlistService.getWishlists(username);
        });
    }

    @Test
    void deleteWishlist_MovieExistsInWishlist_RemovesMovieAndUpdateWishlist() {
        // Given
        String username = "user1";
        String movieId = "tt1234567";
        Wishlist wishlist = new Wishlist();
        wishlist.setUsername(username);
        Movie movie = new Movie();
        movie.setImdbid(movieId);

        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        wishlistDto.setMovies(List.of());
        wishlist.setMovies(new ArrayList<>(Collections.singletonList(movie))); // Use new ArrayList<>() to create a modifiable list
        when(wishlistRepository.findById(username)).thenReturn(Optional.of(wishlist));

        when(modelMapper.map(wishlist, WishlistDto.class)).thenReturn(wishlistDto);
        // When
        WishlistDto result = wishlistService.deleteWishlist(username, movieId);

        System.out.println(result);
        // Then
        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
        assertEquals(0, result.getMovies().size());
    }

    @Test
    void addWishlist_WishlistExists_AddsMovieToWishlist() {
        // Given
        String username = "user1";
        String movieId = "tt1234567";
        MovieDto movieDto = new MovieDto();
        movieDto.setImdbid(movieId);

        Movie movie = new Movie();
        movie.setImdbid(movieId);
        Wishlist existingWishlist = new Wishlist();
        existingWishlist.setUsername(username);
        existingWishlist.setMovies(List.of(movie)); // Initialize movies list

        when(wishlistRepository.findById(username)).thenReturn(Optional.of(existingWishlist));
        when(modelMapper.map(movieDto, Movie.class)).thenReturn(movie);

        // Mock the behavior of wishlistRepository.save(existingWishlist) to return existingWishlist
        when(wishlistRepository.save(existingWishlist)).thenReturn(existingWishlist);

        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        wishlistDto.setMovies(List.of(movieDto));

        when(modelMapper.map(existingWishlist, WishlistDto.class)).thenReturn(wishlistDto);

        // When
        WishlistDto result = wishlistService.addWishlist(username, movieDto);

        // Then
        assertEquals(username, result.getUsername());
        verify(wishlistRepository, times(1)).save(existingWishlist);
    }


    @Test
    void addWishlist_WishlistDoesNotExist_CreatesNewWishlist() {
        // Given
        String username = "user1";
        String movieId = "tt1234567";
        MovieDto movieDto = new MovieDto();
        movieDto.setImdbid(movieId);

        Movie movie = new Movie();
        movie.setImdbid(movieId);
        Wishlist newWishlist = new Wishlist();
        newWishlist.setUsername(username);
        newWishlist.setMovies(List.of(movie)); // Initialize movies list

        when(wishlistRepository.findById(username)).thenReturn(Optional.empty());
        when(modelMapper.map(movieDto, Movie.class)).thenReturn(movie);

        // Mock the behavior of wishlistRepository.save(newWishlist) to return newWishlist
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(newWishlist);

        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        wishlistDto.setMovies(List.of(movieDto));

        when(modelMapper.map(newWishlist, WishlistDto.class)).thenReturn(wishlistDto);

        // When
        WishlistDto result = wishlistService.addWishlist(username, movieDto);

        // Then
        assertEquals(username, result.getUsername());
        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

}

