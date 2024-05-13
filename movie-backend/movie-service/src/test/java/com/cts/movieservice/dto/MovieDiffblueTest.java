package com.cts.movieservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Movie.class})
@ExtendWith(SpringExtension.class)
class MovieDiffblueTest {
    @Autowired
    private Movie movie;

    /**
     * Method under test: {@link Movie#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        // Arrange, Act and Assert
        assertFalse(movie.canEqual("Other"));
        assertTrue(movie.canEqual(movie));
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals() {
        // Arrange, Act and Assert
        assertNotEquals(new Movie(), null);
        assertNotEquals(new Movie(), "Different type to Movie");
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals2() {
        // Arrange
        Movie movie = new Movie("42", 1, "Dr", "Image","Thumbnail", "The characteristics of someone or something", new ArrayList<>(),
                10.0f, 1, "Imdb Link", "Imdbid");

        // Act and Assert
        assertNotEquals(movie, new Movie());
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals3() {
        // Arrange
        Movie movie = new Movie();
        movie.setId("42");

        // Act and Assert
        assertNotEquals(movie, new Movie());
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals4() {
        // Arrange
        Movie movie = new Movie();
        movie.setTitle("Dr");

        // Act and Assert
        assertNotEquals(movie, new Movie());
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals5() {
        // Arrange
        Movie movie = new Movie();
        movie.setImage("Image");

        // Act and Assert
        assertNotEquals(movie, new Movie());
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals6() {
        // Arrange
        Movie movie = new Movie();
        movie.setDescription("The characteristics of someone or something");

        // Act and Assert
        assertNotEquals(movie, new Movie());
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals7() {
        // Arrange
        Movie movie = new Movie();
        movie.setGenre(new ArrayList<>());

        // Act and Assert
        assertNotEquals(movie, new Movie());
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals8() {
        // Arrange
        Movie movie = new Movie();
        movie.setRating(10.0f);

        // Act and Assert
        assertNotEquals(movie, new Movie());
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals9() {
        // Arrange
        Movie movie = new Movie();
        movie.setYear(1);

        // Act and Assert
        assertNotEquals(movie, new Movie());
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals10() {
        // Arrange
        Movie movie = new Movie();
        movie.setImdbLink("Imdb Link");

        // Act and Assert
        assertNotEquals(movie, new Movie());
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals11() {
        // Arrange
        Movie movie = new Movie();
        movie.setImdbid("Imdbid");

        // Act and Assert
        assertNotEquals(movie, new Movie());
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals12() {
        // Arrange
        Movie movie = new Movie();

        Movie movie2 = new Movie();
        movie2.setId("42");

        // Act and Assert
        assertNotEquals(movie, movie2);
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals13() {
        // Arrange
        Movie movie = new Movie();

        Movie movie2 = new Movie();
        movie2.setTitle("Dr");

        // Act and Assert
        assertNotEquals(movie, movie2);
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals14() {
        // Arrange
        Movie movie = new Movie();

        Movie movie2 = new Movie();
        movie2.setImage("Image");

        // Act and Assert
        assertNotEquals(movie, movie2);
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals15() {
        // Arrange
        Movie movie = new Movie();

        Movie movie2 = new Movie();
        movie2.setDescription("The characteristics of someone or something");

        // Act and Assert
        assertNotEquals(movie, movie2);
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals16() {
        // Arrange
        Movie movie = new Movie();

        Movie movie2 = new Movie();
        movie2.setGenre(new ArrayList<>());

        // Act and Assert
        assertNotEquals(movie, movie2);
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals17() {
        // Arrange
        Movie movie = new Movie();

        Movie movie2 = new Movie();
        movie2.setImdbLink("Imdb Link");

        // Act and Assert
        assertNotEquals(movie, movie2);
    }

    /**
     * Method under test: {@link Movie#equals(Object)}
     */
    @Test
    void testEquals18() {
        // Arrange
        Movie movie = new Movie();

        Movie movie2 = new Movie();
        movie2.setImdbid("Imdbid");

        // Act and Assert
        assertNotEquals(movie, movie2);
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link Movie#equals(Object)}
     *   <li>{@link Movie#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Movie movie = new Movie();

        // Act and Assert
        assertEquals(movie, movie);
        int expectedHashCodeResult = movie.hashCode();
        assertEquals(expectedHashCodeResult, movie.hashCode());
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link Movie#equals(Object)}
     *   <li>{@link Movie#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode2() {
        // Arrange
        Movie movie = new Movie();
        Movie movie2 = new Movie();

        // Act and Assert
        assertEquals(movie, movie2);
        int expectedHashCodeResult = movie.hashCode();
        assertEquals(expectedHashCodeResult, movie2.hashCode());
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link Movie#equals(Object)}
     *   <li>{@link Movie#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode3() {
        // Arrange
        Movie movie = new Movie("42", 1, "Dr", "Image","Thumbnail", "The characteristics of someone or something", new ArrayList<>(),
                10.0f, 1, "Imdb Link", "Imdbid");
        Movie movie2 = new Movie("42", 1, "Dr", "Image","Thumbnail", "The characteristics of someone or something", new ArrayList<>(),
                10.0f, 1, "Imdb Link", "Imdbid");

        // Act and Assert
        assertEquals(movie, movie2);
        int expectedHashCodeResult = movie.hashCode();
        assertEquals(expectedHashCodeResult, movie2.hashCode());
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link Movie#Movie()}
     *   <li>{@link Movie#setDescription(String)}
     *   <li>{@link Movie#setGenre(List)}
     *   <li>{@link Movie#setId(String)}
     *   <li>{@link Movie#setImage(String)}
     *   <li>{@link Movie#setImdbLink(String)}
     *   <li>{@link Movie#setImdbid(String)}
     *   <li>{@link Movie#setRank(int)}
     *   <li>{@link Movie#setRating(float)}
     *   <li>{@link Movie#setTitle(String)}
     *   <li>{@link Movie#setYear(int)}
     *   <li>{@link Movie#toString()}
     *   <li>{@link Movie#getDescription()}
     *   <li>{@link Movie#getGenre()}
     *   <li>{@link Movie#getId()}
     *   <li>{@link Movie#getImage()}
     *   <li>{@link Movie#getImdbLink()}
     *   <li>{@link Movie#getImdbid()}
     *   <li>{@link Movie#getRank()}
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
        actualMovie.setId("42");
        actualMovie.setImage("Image");
        actualMovie.setImdbLink("Imdb Link");
        actualMovie.setImdbid("Imdbid");
        actualMovie.setRank(1);
        actualMovie.setThumbnail("Thumbnail");
        actualMovie.setRating(10.0f);
        actualMovie.setTitle("Dr");
        actualMovie.setYear(1);
        String actualToStringResult = actualMovie.toString();
        String actualDescription = actualMovie.getDescription();
        List<String> actualGenre = actualMovie.getGenre();
        String actualId = actualMovie.getId();
        String actualImage = actualMovie.getImage();
        String actualThumbnail = actualMovie.getThumbnail();
        String actualImdbLink = actualMovie.getImdbLink();
        String actualImdbid = actualMovie.getImdbid();
        int actualRank = actualMovie.getRank();
        float actualRating = actualMovie.getRating();
        String actualTitle = actualMovie.getTitle();

        // Assert that nothing has changed
        assertEquals("42", actualId);
        assertEquals("Dr", actualTitle);
        assertEquals("Image", actualImage);
        assertEquals("Imdb Link", actualImdbLink);
        assertEquals("Thumbnail", actualThumbnail);
        assertEquals("Imdbid", actualImdbid);
        assertEquals("Movie(id=42, rank=1, title=Dr, image=Image, thumbnail=Thumbnail, description=The characteristics of someone or something,"
                + " genre=[], rating=10.0, year=1, imdbLink=Imdb Link, imdbid=Imdbid)", actualToStringResult);
        assertEquals("The characteristics of someone or something", actualDescription);
        assertEquals(1, actualRank);
        assertEquals(1, actualMovie.getYear());
        assertEquals(10.0f, actualRating);
        assertSame(genre, actualGenre);
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>
     * {@link Movie#Movie(String, int, String, String, String, List, float, int, String, String)}
     *   <li>{@link Movie#setDescription(String)}
     *   <li>{@link Movie#setGenre(List)}
     *   <li>{@link Movie#setId(String)}
     *   <li>{@link Movie#setImage(String)}
     *   <li>{@link Movie#setImdbLink(String)}
     *   <li>{@link Movie#setImdbid(String)}
     *   <li>{@link Movie#setRank(int)}
     *   <li>{@link Movie#setRating(float)}
     *   <li>{@link Movie#setTitle(String)}
     *   <li>{@link Movie#setYear(int)}
     *   <li>{@link Movie#toString()}
     *   <li>{@link Movie#getDescription()}
     *   <li>{@link Movie#getGenre()}
     *   <li>{@link Movie#getId()}
     *   <li>{@link Movie#getImage()}
     *   <li>{@link Movie#getImdbLink()}
     *   <li>{@link Movie#getImdbid()}
     *   <li>{@link Movie#getRank()}
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
        Movie actualMovie = new Movie("42", 1, "Dr", "Image","Thumbnail", "The characteristics of someone or something", genre, 10.0f,
                1, "Imdb Link", "Imdbid");
        actualMovie.setDescription("The characteristics of someone or something");
        ArrayList<String> genre2 = new ArrayList<>();
        actualMovie.setGenre(genre2);
        actualMovie.setId("42");
        actualMovie.setImage("Image");
        actualMovie.setThumbnail("Thumbnail");
        actualMovie.setImdbLink("Imdb Link");
        actualMovie.setImdbid("Imdbid");
        actualMovie.setRank(1);
        actualMovie.setRating(10.0f);
        actualMovie.setTitle("Dr");
        actualMovie.setYear(1);
        String actualToStringResult = actualMovie.toString();
        String actualDescription = actualMovie.getDescription();
        List<String> actualGenre = actualMovie.getGenre();
        String actualId = actualMovie.getId();
        String actualImage = actualMovie.getImage();
        String actualThumbnail = actualMovie.getThumbnail();
        String actualImdbLink = actualMovie.getImdbLink();
        String actualImdbid = actualMovie.getImdbid();
        int actualRank = actualMovie.getRank();
        float actualRating = actualMovie.getRating();
        String actualTitle = actualMovie.getTitle();

        // Assert that nothing has changed
        assertEquals("42", actualId);
        assertEquals("Dr", actualTitle);
        assertEquals("Image", actualImage);
        assertEquals("Imdb Link", actualImdbLink);
        assertEquals("Imdbid", actualImdbid);
        assertEquals("Movie(id=42, rank=1, title=Dr, image=Image, thumbnail=Thumbnail, description=The characteristics of someone or something,"
                + " genre=[], rating=10.0, year=1, imdbLink=Imdb Link, imdbid=Imdbid)", actualToStringResult);
        assertEquals("The characteristics of someone or something", actualDescription);
        assertEquals(1, actualRank);
        assertEquals(1, actualMovie.getYear());
        assertEquals(10.0f, actualRating);
        assertEquals(genre, actualGenre);
        assertSame(genre2, actualGenre);
    }
}
