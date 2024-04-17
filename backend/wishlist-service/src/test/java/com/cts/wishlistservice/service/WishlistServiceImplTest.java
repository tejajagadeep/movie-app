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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class WishlistServiceImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void testGetWishlists_Success() throws ResourceNotFoundException {
        String username = "testUser";
        Wishlist wishlist = new Wishlist(username, List.of());
        Optional<Wishlist> optionalWishlist = Optional.of(wishlist);

        when(wishlistRepository.findById(username)).thenReturn(optionalWishlist);
        when(modelMapper.map(wishlist, WishlistDto.class)).thenReturn(new WishlistDto());

        WishlistDto wishlistDto = wishlistService.getWishlists(username);

        verify(wishlistRepository).findById(username);
        verify(modelMapper).map(wishlist, WishlistDto.class);

        assertNotNull(wishlistDto);
    }

    @Test
    void testGetWishlists_UsernameNotFound(){
        String username = "testUser";

        when(wishlistRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,()->wishlistService.getWishlists(username));

    }


    @Test
    void testDeleteWishlist_UsernameNotFound() {
        String username = "testUser";
        String movieId = "testMovie";
        when(wishlistRepository.findById(username)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class,()->wishlistService.deleteWishlist(username, movieId));
    }

    @Test
    void testAddWishlist_NewUser() {
        String username = "testUser";
        MovieDto movieDto = new MovieDto();
        Wishlist wishlist = new Wishlist();
        Movie movie = new Movie();
        movie.setImdbid("123");
        movie.setId("top1");
        movieDto.setImdbid("123");
        movieDto.setId("top1");
        wishlist.setUsername(username);
        wishlist.setMovies(List.of(movie));

        // Mock repository behavior to return empty optional
        when(wishlistRepository.findById(username)).thenReturn(Optional.empty());
        // Mock model mapper
        when(modelMapper.map(movieDto, Movie.class)).thenReturn(movie);
        // Mock repository save method
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);

        // Call the service method
        WishlistDto wishlistDto = wishlistService.addWishlist(username, movieDto);

        // Verify method invocations
        verify(wishlistRepository).findById(username);
        verify(modelMapper).map(movieDto, Movie.class);
        verify(wishlistRepository).save(wishlist);

        assertNotNull(wishlistDto);
    }

    @Test
    void testAddWishlist_ExistingUser() {
        String username = "testUser";
        MovieDto movieDto = new MovieDto();
        movieDto.setId("123");
        movieDto.setImdbid("123");
        Movie movie = new Movie();
        // Mock repository behavior
        List<Movie> movies = new ArrayList<>();
        Wishlist wishlist = new Wishlist(username, movies);
        when(wishlistRepository.findById(username)).thenReturn(Optional.of(wishlist));

        when(modelMapper.map(movieDto, Movie.class)).thenReturn(movie);
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);

        // Call the service method
        WishlistDto wishlistDto = wishlistService.addWishlist(username, movieDto);
        System.out.println(wishlistDto);
        assertEquals(wishlist.getUsername(),wishlistDto.getUsername());

    }

}
