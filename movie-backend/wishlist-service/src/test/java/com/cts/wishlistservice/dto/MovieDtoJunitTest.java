package com.cts.wishlistservice.dto;

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

@ContextConfiguration(classes = {MovieDto.class})
@ExtendWith(SpringExtension.class)
class MovieDtoJunitTest {
    @Autowired
    private MovieDto movieDto;

    /**
     * Method under test: {@link MovieDto#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        // Arrange, Act and Assert
        assertFalse(movieDto.canEqual("Other"));
        assertTrue(movieDto.canEqual(movieDto));
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals() {
        // Arrange, Act and Assert
        assertNotEquals(new MovieDto(), null);
        assertNotEquals(new MovieDto(), "Different type to MovieDto");
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals2() {
        // Arrange
        MovieDto movieDto = new MovieDto("Imdbid", "Dr", "Image", "The characteristics of someone or something",
                new ArrayList<>(), 10.0f, 1, "Imdb Link");

        // Act and Assert
        assertNotEquals(movieDto, new MovieDto());
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals3() {
        // Arrange
        MovieDto movieDto = new MovieDto();
        movieDto.setImdbid("Imdbid");

        // Act and Assert
        assertNotEquals(movieDto, new MovieDto());
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals4() {
        // Arrange
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Dr");

        // Act and Assert
        assertNotEquals(movieDto, new MovieDto());
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals5() {
        // Arrange
        MovieDto movieDto = new MovieDto();
        movieDto.setImage("Image");

        // Act and Assert
        assertNotEquals(movieDto, new MovieDto());
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals6() {
        // Arrange
        MovieDto movieDto = new MovieDto();
        movieDto.setDescription("The characteristics of someone or something");

        // Act and Assert
        assertNotEquals(movieDto, new MovieDto());
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals7() {
        // Arrange
        MovieDto movieDto = new MovieDto();
        movieDto.setGenre(new ArrayList<>());

        // Act and Assert
        assertNotEquals(movieDto, new MovieDto());
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals8() {
        // Arrange
        MovieDto movieDto = new MovieDto();
        movieDto.setYear(1);

        // Act and Assert
        assertNotEquals(movieDto, new MovieDto());
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals9() {
        // Arrange
        MovieDto movieDto = new MovieDto();
        movieDto.setImdbLink("Imdb Link");

        // Act and Assert
        assertNotEquals(movieDto, new MovieDto());
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals10() {
        // Arrange
        MovieDto movieDto = new MovieDto();

        MovieDto movieDto2 = new MovieDto();
        movieDto2.setImdbid("Imdbid");

        // Act and Assert
        assertNotEquals(movieDto, movieDto2);
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals11() {
        // Arrange
        MovieDto movieDto = new MovieDto();

        MovieDto movieDto2 = new MovieDto();
        movieDto2.setTitle("Dr");

        // Act and Assert
        assertNotEquals(movieDto, movieDto2);
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals12() {
        // Arrange
        MovieDto movieDto = new MovieDto();

        MovieDto movieDto2 = new MovieDto();
        movieDto2.setImage("Image");

        // Act and Assert
        assertNotEquals(movieDto, movieDto2);
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals13() {
        // Arrange
        MovieDto movieDto = new MovieDto();

        MovieDto movieDto2 = new MovieDto();
        movieDto2.setDescription("The characteristics of someone or something");

        // Act and Assert
        assertNotEquals(movieDto, movieDto2);
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals14() {
        // Arrange
        MovieDto movieDto = new MovieDto();

        MovieDto movieDto2 = new MovieDto();
        movieDto2.setGenre(new ArrayList<>());

        // Act and Assert
        assertNotEquals(movieDto, movieDto2);
    }

    /**
     * Method under test: {@link MovieDto#equals(Object)}
     */
    @Test
    void testEquals15() {
        // Arrange
        MovieDto movieDto = new MovieDto();

        MovieDto movieDto2 = new MovieDto();
        movieDto2.setImdbLink("Imdb Link");

        // Act and Assert
        assertNotEquals(movieDto, movieDto2);
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link MovieDto#equals(Object)}
     *   <li>{@link MovieDto#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        MovieDto movieDto = new MovieDto();

        // Act and Assert
        assertEquals(movieDto, movieDto);
        int expectedHashCodeResult = movieDto.hashCode();
        assertEquals(expectedHashCodeResult, movieDto.hashCode());
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link MovieDto#equals(Object)}
     *   <li>{@link MovieDto#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode2() {
        // Arrange
        MovieDto movieDto = new MovieDto();
        MovieDto movieDto2 = new MovieDto();

        // Act and Assert
        assertEquals(movieDto, movieDto2);
        int expectedHashCodeResult = movieDto.hashCode();
        assertEquals(expectedHashCodeResult, movieDto2.hashCode());
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link MovieDto#equals(Object)}
     *   <li>{@link MovieDto#hashCode()}
     * </ul>
     */
    @Test
    void testEqualsAndHashCode3() {
        // Arrange
        MovieDto movieDto = new MovieDto("Imdbid", "Dr", "Image", "The characteristics of someone or something",
                new ArrayList<>(), 10.0f, 1, "Imdb Link");
        MovieDto movieDto2 = new MovieDto("Imdbid", "Dr", "Image", "The characteristics of someone or something",
                new ArrayList<>(), 10.0f, 1, "Imdb Link");

        // Act and Assert
        assertEquals(movieDto, movieDto2);
        int expectedHashCodeResult = movieDto.hashCode();
        assertEquals(expectedHashCodeResult, movieDto2.hashCode());
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>{@link MovieDto#MovieDto()}
     *   <li>{@link MovieDto#setDescription(String)}
     *   <li>{@link MovieDto#setGenre(List)}
     *   <li>{@link MovieDto#setImage(String)}
     *   <li>{@link MovieDto#setImdbLink(String)}
     *   <li>{@link MovieDto#setImdbid(String)}
     *   <li>{@link MovieDto#setRating(float)}
     *   <li>{@link MovieDto#setTitle(String)}
     *   <li>{@link MovieDto#setYear(int)}
     *   <li>{@link MovieDto#toString()}
     *   <li>{@link MovieDto#getDescription()}
     *   <li>{@link MovieDto#getGenre()}
     *   <li>{@link MovieDto#getImage()}
     *   <li>{@link MovieDto#getImdbLink()}
     *   <li>{@link MovieDto#getImdbid()}
     *   <li>{@link MovieDto#getRating()}
     *   <li>{@link MovieDto#getTitle()}
     *   <li>{@link MovieDto#getYear()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        MovieDto actualMovieDto = new MovieDto();
        actualMovieDto.setDescription("The characteristics of someone or something");
        ArrayList<String> genre = new ArrayList<>();
        actualMovieDto.setGenre(genre);
        actualMovieDto.setImage("Image");
        actualMovieDto.setImdbLink("Imdb Link");
        actualMovieDto.setImdbid("Imdbid");
        actualMovieDto.setRating(10.0f);
        actualMovieDto.setTitle("Dr");
        actualMovieDto.setYear(1);
        String actualToStringResult = actualMovieDto.toString();
        String actualDescription = actualMovieDto.getDescription();
        List<String> actualGenre = actualMovieDto.getGenre();
        String actualImage = actualMovieDto.getImage();
        String actualImdbLink = actualMovieDto.getImdbLink();
        String actualImdbid = actualMovieDto.getImdbid();
        float actualRating = actualMovieDto.getRating();
        String actualTitle = actualMovieDto.getTitle();

        // Assert that nothing has changed
        assertEquals("Dr", actualTitle);
        assertEquals("Image", actualImage);
        assertEquals("Imdb Link", actualImdbLink);
        assertEquals("Imdbid", actualImdbid);
        assertEquals(
                "MovieDto(imdbid=Imdbid, title=Dr, image=Image, description=The characteristics of someone or something,"
                        + " genre=[], rating=10.0, year=1, imdbLink=Imdb Link)",
                actualToStringResult);
        assertEquals("The characteristics of someone or something", actualDescription);
        assertEquals(1, actualMovieDto.getYear());
        assertEquals(10.0f, actualRating);
        assertSame(genre, actualGenre);
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>
     * {@link MovieDto#MovieDto(String, String, String, String, List, float, int, String)}
     *   <li>{@link MovieDto#setDescription(String)}
     *   <li>{@link MovieDto#setGenre(List)}
     *   <li>{@link MovieDto#setImage(String)}
     *   <li>{@link MovieDto#setImdbLink(String)}
     *   <li>{@link MovieDto#setImdbid(String)}
     *   <li>{@link MovieDto#setRating(float)}
     *   <li>{@link MovieDto#setTitle(String)}
     *   <li>{@link MovieDto#setYear(int)}
     *   <li>{@link MovieDto#toString()}
     *   <li>{@link MovieDto#getDescription()}
     *   <li>{@link MovieDto#getGenre()}
     *   <li>{@link MovieDto#getImage()}
     *   <li>{@link MovieDto#getImdbLink()}
     *   <li>{@link MovieDto#getImdbid()}
     *   <li>{@link MovieDto#getRating()}
     *   <li>{@link MovieDto#getTitle()}
     *   <li>{@link MovieDto#getYear()}
     * </ul>
     */
    @Test
    void testGettersAndSetters2() {
        // Arrange
        ArrayList<String> genre = new ArrayList<>();

        // Act
        MovieDto actualMovieDto = new MovieDto("Imdbid", "Dr", "Image", "The characteristics of someone or something",
                genre, 10.0f, 1, "Imdb Link");
        actualMovieDto.setDescription("The characteristics of someone or something");
        ArrayList<String> genre2 = new ArrayList<>();
        actualMovieDto.setGenre(genre2);
        actualMovieDto.setImage("Image");
        actualMovieDto.setImdbLink("Imdb Link");
        actualMovieDto.setImdbid("Imdbid");
        actualMovieDto.setRating(10.0f);
        actualMovieDto.setTitle("Dr");
        actualMovieDto.setYear(1);
        String actualToStringResult = actualMovieDto.toString();
        String actualDescription = actualMovieDto.getDescription();
        List<String> actualGenre = actualMovieDto.getGenre();
        String actualImage = actualMovieDto.getImage();
        String actualImdbLink = actualMovieDto.getImdbLink();
        String actualImdbid = actualMovieDto.getImdbid();
        float actualRating = actualMovieDto.getRating();
        String actualTitle = actualMovieDto.getTitle();

        // Assert that nothing has changed
        assertEquals("Dr", actualTitle);
        assertEquals("Image", actualImage);
        assertEquals("Imdb Link", actualImdbLink);
        assertEquals("Imdbid", actualImdbid);
        assertEquals(
                "MovieDto(imdbid=Imdbid, title=Dr, image=Image, description=The characteristics of someone or something,"
                        + " genre=[], rating=10.0, year=1, imdbLink=Imdb Link)",
                actualToStringResult);
        assertEquals("The characteristics of someone or something", actualDescription);
        assertEquals(1, actualMovieDto.getYear());
        assertEquals(10.0f, actualRating);
        assertEquals(genre, actualGenre);
        assertSame(genre2, actualGenre);
    }
}
