package com.cts.wishlistservice.controller;

import static org.mockito.Mockito.when;

import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;
import com.cts.wishlistservice.filter.JwtService;
import com.cts.wishlistservice.service.WishlistService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {WishlistController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class WishlistControllerJunitTest {
    @MockBean
    private JwtService jwtService;

    @Autowired
    private WishlistController wishlistController;

    @MockBean
    private WishlistService wishlistService;

    /**
     * Method under test:
     * {@link WishlistController#deleteWishlist(String, String, String)}
     */
    @Test
    void testDeleteWishlist() throws Exception {
        // Arrange
        when(wishlistService.deleteWishlist(Mockito.<String>any(), Mockito.<String>any())).thenReturn(new WishlistDto());
        when(jwtService.isTokenValid(Mockito.<String>any(), Mockito.<String>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1.0/private/wishlist")
                .param("id", "foo")
                .param("username", "foo")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(wishlistController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"username\":null,\"movies\":null}"));
    }

    /**
     * Method under test:
     * {@link WishlistController#addWishlist(String, String, MovieDto)}
     */
    @Test
    void testAddWishlist() throws Exception {
        // Arrange
        when(wishlistService.addWishlist(Mockito.<String>any(), Mockito.<MovieDto>any())).thenReturn(new WishlistDto());
        when(jwtService.isTokenValid(Mockito.<String>any(), Mockito.<String>any())).thenReturn(true);

        MovieDto movieDto = new MovieDto();
        movieDto.setDescription("The characteristics of someone or something");
        movieDto.setGenre(new ArrayList<>());
        movieDto.setImage("Image");
        movieDto.setImdbLink("Imdb Link");
        movieDto.setImdbid("Imdbid");
        movieDto.setRating(10.0f);
        movieDto.setTitle("Dr");
        movieDto.setYear(1);
        String content = (new ObjectMapper()).writeValueAsString(movieDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1.0/private/wishlist/{username}", "janedoe")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(wishlistController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"username\":null,\"movies\":null}"));
    }

    /**
     * Method under test: {@link WishlistController#getWishlist(String, String)}
     */
    @Test
    void testGetWishlist() throws Exception {
        // Arrange
        when(wishlistService.getWishlists(Mockito.<String>any())).thenReturn(new WishlistDto());
        when(jwtService.isTokenValid(Mockito.<String>any(), Mockito.<String>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1.0/private/wishlist/{username}", "janedoe")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(wishlistController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"username\":null,\"movies\":null}"));
    }
}
