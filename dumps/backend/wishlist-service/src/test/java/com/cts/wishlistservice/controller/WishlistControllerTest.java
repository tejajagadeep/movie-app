package com.cts.wishlistservice.controller;

import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;
import com.cts.wishlistservice.exception.ResourceNotFoundException;
import com.cts.wishlistservice.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(WishlistController.class)
@ContextConfiguration(classes = WishlistController.class)
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    @Test
    @WithMockUser
    void testGetWishlist2() throws Exception {
        String username = "user";
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        MovieDto movie = new MovieDto();
        movie.setTitle("Movie 1");

        wishlistDto.setMovies(List.of(movie));
        when(wishlistService.getWishlists(anyString())).thenReturn(wishlistDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/private/wishlist/{username}", "user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movies").isEmpty());
    }

    @Test
    @WithMockUser
    void testGetWishlist() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "user";
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        MovieDto movie = new MovieDto();
        movie.setTitle("Movie 1");

        wishlistDto.setMovies(List.of(movie));

        when(wishlistService.getWishlists(username)).thenReturn(wishlistDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/private/wishlist/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movies[0].title").value("Movie 1"));
    }

    @Test
    @WithMockUser
    void testDeleteWishlist() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "user";
        String id = "1";

        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Movie 1");
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        wishlistDto.setMovies(List.of(movieDto));

        when(wishlistService.deleteWishlist(username, id)).thenReturn(wishlistDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1.0/private/wishlist")
                        .param("username", username)
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movies[0].title").value("Movie 1"));
    }

    @Test
    @WithMockUser
    void testAddWishlist() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "user";
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Movie 1");
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        wishlistDto.setMovies(List.of(movieDto));
        when(wishlistService.addWishlist(username, movieDto)).thenReturn(wishlistDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/private/wishlist/{username}", username)
                        .content("{\"id\":\"1\",\"title\":\"Movie 1\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.movies[0].title").value("Movie 1"));
    }

    @Test
    void testGetWishlist_UnauthorizedException() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "user";
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        MovieDto movie = new MovieDto();
        movie.setTitle("Movie 1");

        wishlistDto.setMovies(List.of(movie));

        when(wishlistService.getWishlists(username)).thenReturn(wishlistDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/private/wishlist/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testGetWishlist_notFound() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "user";

        when(wishlistService.getWishlists(username)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.0/private/wishlist/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteWishlist_UnauthorizedException() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "user";
        String id = "1";

        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Movie 1");
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        wishlistDto.setMovies(List.of(movieDto));

        when(wishlistService.deleteWishlist(username, id)).thenReturn(wishlistDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1.0/private/wishlist")
                        .param("username", username)
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testDeleteWishlist_notfound() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "user";
        String id = "1";

        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Movie 1");
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        wishlistDto.setMovies(List.of(movieDto));

        when(wishlistService.deleteWishlist(username, id)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1.0/private/wishlist")
                        .param("username", username)
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testAddWishlist_UnauthorizedException() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "user";
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Movie 1");
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        wishlistDto.setMovies(List.of(movieDto));
        when(wishlistService.addWishlist(username, movieDto)).thenReturn(wishlistDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/private/wishlist/{username}", username)
                        .content("{\"id\":\"1\",\"title\":\"Movie 1\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
    @Test
    @WithMockUser
    void testAddWishlist_notfound() throws Exception {
        String token = "Bearer <your_token_here>";
        String username = "user";
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Movie 1");
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setUsername(username);
        wishlistDto.setMovies(List.of(movieDto));
        when(wishlistService.addWishlist(username, movieDto)).thenThrow(new ResourceNotFoundException(""));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/private/wishlist/{username}", username)
                        .content("{\"id\":\"1\",\"title\":\"Movie 1\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
