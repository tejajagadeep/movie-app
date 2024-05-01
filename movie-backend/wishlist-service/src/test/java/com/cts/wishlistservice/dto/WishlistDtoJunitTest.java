package com.cts.wishlistservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WishlistDto.class})
@ExtendWith(SpringExtension.class)
class WishlistDtoJunitTest {
    @Autowired
    private WishlistDto wishlistDto;

    /**
     * Method under test: {@link WishlistDto#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        // Arrange, Act and Assert
        assertFalse(wishlistDto.canEqual("Other"));
        assertTrue(wishlistDto.canEqual(wishlistDto));
    }

    /**
     * Method under test: {@link WishlistDto#equals(Object)}
     */
    @Test
    void testEquals() {
        // Arrange, Act and Assert
        assertNotEquals(new WishlistDto(), null);
        assertNotEquals(new WishlistDto(), "Different type to WishlistDto");
    }

    /**
     * Method under test: {@link WishlistDto#equals(Object)}
     */
    @Test
    void testEquals2() {
        // Arrange
        WishlistDto wishlistDto = new WishlistDto("janedoe", new ArrayList<>());

        // Act and Assert
        assertNotEquals(wishlistDto, new WishlistDto());
    }

    /**
     * Method under test: {@link WishlistDto#equals(Object)}
     */
    @Test
    void testEquals3() {
        // Arrange
        WishlistDto wishlistDto = new WishlistDto();

        // Act and Assert
        assertNotEquals(wishlistDto, new WishlistDto("janedoe", new ArrayList<>()));
    }

    /**
     * Method under test: {@link WishlistDto#equals(Object)}
     */
    @Test
    void testEquals4() {
        // Arrange
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setMovies(new ArrayList<>());

        // Act and Assert
        assertNotEquals(wishlistDto, new WishlistDto());
    }

    /**
     * Method under test: {@link WishlistDto#equals(Object)}
     */
    @Test
    void testEquals5() {
        // Arrange
        WishlistDto wishlistDto = new WishlistDto();

        WishlistDto wishlistDto2 = new WishlistDto();
        wishlistDto2.setMovies(new ArrayList<>());

        // Act and Assert
        assertNotEquals(wishlistDto, wishlistDto2);
    }

    /**
     * Method under test: {@link WishlistDto#equals(Object)}
     */
    @Test
    void testEquals6() {
        // Arrange
        ArrayList<MovieDto> movies = new ArrayList<>();
        movies.add(mock(MovieDto.class));
        WishlistDto wishlistDto = new WishlistDto("janedoe", movies);

        // Act and Assert
        assertNotEquals(wishlistDto, new WishlistDto());
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link WishlistDto#equals(Object)}
     *   <li>{@link WishlistDto#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        WishlistDto wishlistDto = new WishlistDto();

        // Act and Assert
        assertEquals(wishlistDto, wishlistDto);
        int expectedHashCodeResult = wishlistDto.hashCode();
        assertEquals(expectedHashCodeResult, wishlistDto.hashCode());
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link WishlistDto#equals(Object)}
     *   <li>{@link WishlistDto#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode2() {
        // Arrange
        WishlistDto wishlistDto = new WishlistDto();
        WishlistDto wishlistDto2 = new WishlistDto();

        // Act and Assert
        assertEquals(wishlistDto, wishlistDto2);
        int expectedHashCodeResult = wishlistDto.hashCode();
        assertEquals(expectedHashCodeResult, wishlistDto2.hashCode());
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link WishlistDto#equals(Object)}
     *   <li>{@link WishlistDto#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode3() {
        // Arrange
        WishlistDto wishlistDto = new WishlistDto("janedoe", new ArrayList<>());
        WishlistDto wishlistDto2 = new WishlistDto("janedoe", new ArrayList<>());

        // Act and Assert
        assertEquals(wishlistDto, wishlistDto2);
        int expectedHashCodeResult = wishlistDto.hashCode();
        assertEquals(expectedHashCodeResult, wishlistDto2.hashCode());
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link WishlistDto#WishlistDto()}
     *   <li>{@link WishlistDto#setMovies(List)}
     *   <li>{@link WishlistDto#setUsername(String)}
     *   <li>{@link WishlistDto#toString()}
     *   <li>{@link WishlistDto#getMovies()}
     *   <li>{@link WishlistDto#getUsername()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        WishlistDto actualWishlistDto = new WishlistDto();
        ArrayList<MovieDto> movies = new ArrayList<>();
        actualWishlistDto.setMovies(movies);
        actualWishlistDto.setUsername("janedoe");
        String actualToStringResult = actualWishlistDto.toString();
        List<MovieDto> actualMovies = actualWishlistDto.getMovies();

        // Assert that nothing has changed
        assertEquals("WishlistDto(username=janedoe, movies=[])", actualToStringResult);
        assertEquals("janedoe", actualWishlistDto.getUsername());
        assertSame(movies, actualMovies);
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link WishlistDto#WishlistDto(String, List)}
     *   <li>{@link WishlistDto#setMovies(List)}
     *   <li>{@link WishlistDto#setUsername(String)}
     *   <li>{@link WishlistDto#toString()}
     *   <li>{@link WishlistDto#getMovies()}
     *   <li>{@link WishlistDto#getUsername()}
     * </ul>
     */
    @Test
    void testGettersAndSetters2() {
        // Arrange
        ArrayList<MovieDto> movies = new ArrayList<>();

        // Act
        WishlistDto actualWishlistDto = new WishlistDto("janedoe", movies);
        ArrayList<MovieDto> movies2 = new ArrayList<>();
        actualWishlistDto.setMovies(movies2);
        actualWishlistDto.setUsername("janedoe");
        String actualToStringResult = actualWishlistDto.toString();
        List<MovieDto> actualMovies = actualWishlistDto.getMovies();

        // Assert that nothing has changed
        assertEquals("WishlistDto(username=janedoe, movies=[])", actualToStringResult);
        assertEquals("janedoe", actualWishlistDto.getUsername());
        assertEquals(movies, actualMovies);
        assertSame(movies2, actualMovies);
    }
}
