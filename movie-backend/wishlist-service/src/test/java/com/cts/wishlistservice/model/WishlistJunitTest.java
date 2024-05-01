package com.cts.wishlistservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class WishlistJunitTest {
    /**
     * Methods under test:
     * <ul>
     *   <li>{@link Wishlist#Wishlist()}
     *   <li>{@link Wishlist#setMovies(List)}
     *   <li>{@link Wishlist#setUsername(String)}
     *   <li>{@link Wishlist#toString()}
     *   <li>{@link Wishlist#getMovies()}
     *   <li>{@link Wishlist#getUsername()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        Wishlist actualWishlist = new Wishlist();
        ArrayList<Movie> movies = new ArrayList<>();
        actualWishlist.setMovies(movies);
        actualWishlist.setUsername("janedoe");
        String actualToStringResult = actualWishlist.toString();
        List<Movie> actualMovies = actualWishlist.getMovies();

        // Assert that nothing has changed
        assertEquals("Wishlist(username=janedoe, movies=[])", actualToStringResult);
        assertEquals("janedoe", actualWishlist.getUsername());
        assertSame(movies, actualMovies);
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link Wishlist#Wishlist(String, List)}
     *   <li>{@link Wishlist#setMovies(List)}
     *   <li>{@link Wishlist#setUsername(String)}
     *   <li>{@link Wishlist#toString()}
     *   <li>{@link Wishlist#getMovies()}
     *   <li>{@link Wishlist#getUsername()}
     * </ul>
     */
    @Test
    void testGettersAndSetters2() {
        // Arrange
        ArrayList<Movie> movies = new ArrayList<>();

        // Act
        Wishlist actualWishlist = new Wishlist("janedoe", movies);
        ArrayList<Movie> movies2 = new ArrayList<>();
        actualWishlist.setMovies(movies2);
        actualWishlist.setUsername("janedoe");
        String actualToStringResult = actualWishlist.toString();
        List<Movie> actualMovies = actualWishlist.getMovies();

        // Assert that nothing has changed
        assertEquals("Wishlist(username=janedoe, movies=[])", actualToStringResult);
        assertEquals("janedoe", actualWishlist.getUsername());
        assertEquals(movies, actualMovies);
        assertSame(movies2, actualMovies);
    }
}
