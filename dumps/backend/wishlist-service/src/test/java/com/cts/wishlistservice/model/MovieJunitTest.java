package com.cts.wishlistservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class MovieJunitTest {
    /**
     * Methods under test:
     * <ul>
     *   <li>{@link Movie#Movie()}
     *   <li>{@link Movie#setDescription(String)}
     *   <li>{@link Movie#setGenre(List)}
     *   <li>{@link Movie#setImage(String)}
     *   <li>{@link Movie#setImdbLink(String)}
     *   <li>{@link Movie#setImdbid(String)}
     *   <li>{@link Movie#setRating(float)}
     *   <li>{@link Movie#setTitle(String)}
     *   <li>{@link Movie#setYear(int)}
     *   <li>{@link Movie#toString()}
     *   <li>{@link Movie#getDescription()}
     *   <li>{@link Movie#getGenre()}
     *   <li>{@link Movie#getImage()}
     *   <li>{@link Movie#getImdbLink()}
     *   <li>{@link Movie#getImdbid()}
     *   <li>{@link Movie#getRating()}
     *   <li>{@link Movie#getTitle()}
     *   <li>{@link Movie#getYear()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        Movie actualMovie = new Movie();
        actualMovie.setDescription("The characteristics of someone or something");
        ArrayList<String> genre = new ArrayList<>();
        actualMovie.setGenre(genre);
        actualMovie.setImage("Image");
        actualMovie.setImdbLink("Imdb Link");
        actualMovie.setImdbid("Imdbid");
        actualMovie.setRating(10.0f);
        actualMovie.setTitle("Dr");
        actualMovie.setYear(1);
        String actualToStringResult = actualMovie.toString();
        String actualDescription = actualMovie.getDescription();
        List<String> actualGenre = actualMovie.getGenre();
        String actualImage = actualMovie.getImage();
        String actualImdbLink = actualMovie.getImdbLink();
        String actualImdbid = actualMovie.getImdbid();
        float actualRating = actualMovie.getRating();
        String actualTitle = actualMovie.getTitle();

        // Assert that nothing has changed
        assertEquals("Dr", actualTitle);
        assertEquals("Image", actualImage);
        assertEquals("Imdb Link", actualImdbLink);
        assertEquals("Imdbid", actualImdbid);
        assertEquals("Movie(imdbid=Imdbid, description=The characteristics of someone or something, title=Dr, image=Image,"
                + " genre=[], rating=10.0, year=1, imdbLink=Imdb Link)", actualToStringResult);
        assertEquals("The characteristics of someone or something", actualDescription);
        assertEquals(1, actualMovie.getYear());
        assertEquals(10.0f, actualRating);
        assertSame(genre, actualGenre);
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>
     * {@link Movie#Movie(String, String, String, String, List, float, int, String)}
     *   <li>{@link Movie#setDescription(String)}
     *   <li>{@link Movie#setGenre(List)}
     *   <li>{@link Movie#setImage(String)}
     *   <li>{@link Movie#setImdbLink(String)}
     *   <li>{@link Movie#setImdbid(String)}
     *   <li>{@link Movie#setRating(float)}
     *   <li>{@link Movie#setTitle(String)}
     *   <li>{@link Movie#setYear(int)}
     *   <li>{@link Movie#toString()}
     *   <li>{@link Movie#getDescription()}
     *   <li>{@link Movie#getGenre()}
     *   <li>{@link Movie#getImage()}
     *   <li>{@link Movie#getImdbLink()}
     *   <li>{@link Movie#getImdbid()}
     *   <li>{@link Movie#getRating()}
     *   <li>{@link Movie#getTitle()}
     *   <li>{@link Movie#getYear()}
     * </ul>
     */
    @Test
    void testGettersAndSetters2() {
        // Arrange
        ArrayList<String> genre = new ArrayList<>();

        // Act
        Movie actualMovie = new Movie("Imdbid", "The characteristics of someone or something", "Dr", "Image", genre, 10.0f,
                1, "Imdb Link");
        actualMovie.setDescription("The characteristics of someone or something");
        ArrayList<String> genre2 = new ArrayList<>();
        actualMovie.setGenre(genre2);
        actualMovie.setImage("Image");
        actualMovie.setImdbLink("Imdb Link");
        actualMovie.setImdbid("Imdbid");
        actualMovie.setRating(10.0f);
        actualMovie.setTitle("Dr");
        actualMovie.setYear(1);
        String actualToStringResult = actualMovie.toString();
        String actualDescription = actualMovie.getDescription();
        List<String> actualGenre = actualMovie.getGenre();
        String actualImage = actualMovie.getImage();
        String actualImdbLink = actualMovie.getImdbLink();
        String actualImdbid = actualMovie.getImdbid();
        float actualRating = actualMovie.getRating();
        String actualTitle = actualMovie.getTitle();

        // Assert that nothing has changed
        assertEquals("Dr", actualTitle);
        assertEquals("Image", actualImage);
        assertEquals("Imdb Link", actualImdbLink);
        assertEquals("Imdbid", actualImdbid);
        assertEquals("Movie(imdbid=Imdbid, description=The characteristics of someone or something, title=Dr, image=Image,"
                + " genre=[], rating=10.0, year=1, imdbLink=Imdb Link)", actualToStringResult);
        assertEquals("The characteristics of someone or something", actualDescription);
        assertEquals(1, actualMovie.getYear());
        assertEquals(10.0f, actualRating);
        assertEquals(genre, actualGenre);
        assertSame(genre2, actualGenre);
    }
}
