package com.cts.wishlistservice.controller;

import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;
import com.cts.wishlistservice.exception.UnAuthorizedException;
import com.cts.wishlistservice.filter.JwtService;
import com.cts.wishlistservice.model.Movie;
import com.cts.wishlistservice.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(WishlistController.class)
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    @MockBean
    private JwtService jwtService;


    @Test
    void testGetWishlist() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "testUser";
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        MovieDto movie = new MovieDto();
        movie.setId("1");
        movie.setTitle("Movie 1");

        wishlistDto.setMovies(List.of(movie));

        when(jwtService.isTokenValid(token.substring(7), username)).thenReturn(true);
        when(wishlistService.getWishlists(username)).thenReturn(wishlistDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/private/wishlist/{username}", username)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movies[0].id").value("1"));
    }

    @Test
    public void testDeleteWishlist() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "testUser";
        String id = "1";

        MovieDto movieDto = new MovieDto();
        movieDto.setId("1");
        movieDto.setTitle("Movie 1");
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        wishlistDto.setMovies(List.of(movieDto));

        when(jwtService.isTokenValid(token.substring(7), username)).thenReturn(true);
        when(wishlistService.deleteWishlist(username, id)).thenReturn(wishlistDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1.0/private/wishlist")
                        .header("Authorization", token)
                        .param("username", username)
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movies[0].id").value("1"));
    }

    @Test
    public void testAddWishlist() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "testUser";
        MovieDto movieDto = new MovieDto();
        movieDto.setId("1");
        movieDto.setTitle("Movie 1");
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        wishlistDto.setMovies(List.of(movieDto));
        when(jwtService.isTokenValid(token.substring(7), username)).thenReturn(true);
        when(wishlistService.addWishlist(username, movieDto)).thenReturn(wishlistDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/private/wishlist/{username}", username)
                        .header("Authorization", token)
                        .content("{\"id\":\"1\",\"title\":\"Movie 1\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.movies[0].title").value("Movie 1"));
    }
}