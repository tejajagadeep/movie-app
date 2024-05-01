package com.cts.movieservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class MovieDetailsDiffblueTest {
    /**
     * Methods under test:
     * <ul>
     *   <li>{@link MovieDetails#MovieDetails()}
     *   <li>{@link MovieDetails#setBigImage(String)}
     *   <li>{@link MovieDetails#setDescription(String)}
     *   <li>{@link MovieDetails#setDirector(List)}
     *   <li>{@link MovieDetails#setGenre(List)}
     *   <li>{@link MovieDetails#setId(String)}
     *   <li>{@link MovieDetails#setImdbLink(String)}
     *   <li>{@link MovieDetails#setImdbid(String)}
     *   <li>{@link MovieDetails#setRank(int)}
     *   <li>{@link MovieDetails#setRating(float)}
     *   <li>{@link MovieDetails#setTitle(String)}
     *   <li>{@link MovieDetails#setTrailerEmbedLink(String)}
     *   <li>{@link MovieDetails#setWriters(List)}
     *   <li>{@link MovieDetails#setYear(int)}
     *   <li>{@link MovieDetails#toString()}
     *   <li>{@link MovieDetails#getBigImage()}
     *   <li>{@link MovieDetails#getDescription()}
     *   <li>{@link MovieDetails#getDirector()}
     *   <li>{@link MovieDetails#getGenre()}
     *   <li>{@link MovieDetails#getId()}
     *   <li>{@link MovieDetails#getImdbLink()}
     *   <li>{@link MovieDetails#getImdbid()}
     *   <li>{@link MovieDetails#getRank()}
     *   <li>{@link MovieDetails#getRating()}
     *   <li>{@link MovieDetails#getTitle()}
     *   <li>{@link MovieDetails#getTrailerEmbedLink()}
     *   <li>{@link MovieDetails#getWriters()}
     *   <li>{@link MovieDetails#getYear()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        MovieDetails actualMovieDetails = new MovieDetails();
        actualMovieDetails.setBigImage("Big Image");
        actualMovieDetails.setDescription("The characteristics of someone or something");
        ArrayList<String> director = new ArrayList<>();
        actualMovieDetails.setDirector(director);
        ArrayList<String> genre = new ArrayList<>();
        actualMovieDetails.setGenre(genre);
        actualMovieDetails.setId("42");
        actualMovieDetails.setImdbLink("Imdb Link");
        actualMovieDetails.setImdbid("Imdbid");
        actualMovieDetails.setRank(1);
        actualMovieDetails.setRating(10.0f);
        actualMovieDetails.setTitle("Dr");
        actualMovieDetails.setTrailerEmbedLink("Trailer Embed Link");
        ArrayList<String> writers = new ArrayList<>();
        actualMovieDetails.setWriters(writers);
        actualMovieDetails.setYear(1);
        String actualToStringResult = actualMovieDetails.toString();
        String actualBigImage = actualMovieDetails.getBigImage();
        String actualDescription = actualMovieDetails.getDescription();
        List<String> actualDirector = actualMovieDetails.getDirector();
        List<String> actualGenre = actualMovieDetails.getGenre();
        String actualId = actualMovieDetails.getId();
        String actualImdbLink = actualMovieDetails.getImdbLink();
        String actualImdbid = actualMovieDetails.getImdbid();
        int actualRank = actualMovieDetails.getRank();
        float actualRating = actualMovieDetails.getRating();
        String actualTitle = actualMovieDetails.getTitle();
        String actualTrailerEmbedLink = actualMovieDetails.getTrailerEmbedLink();
        List<String> actualWriters = actualMovieDetails.getWriters();

        // Assert that nothing has changed
        assertEquals("42", actualId);
        assertEquals("Big Image", actualBigImage);
        assertEquals("Dr", actualTitle);
        assertEquals("Imdb Link", actualImdbLink);
        assertEquals("Imdbid", actualImdbid);
        assertEquals("MovieDetails(id=42, rank=1, title=Dr, description=The characteristics of someone or something,"
                + " bigImage=Big Image, genre=[], rating=10.0, year=1, imdbLink=Imdb Link, imdbid=Imdbid, trailerEmbedLink"
                + "=Trailer Embed Link, director=[], writers=[])", actualToStringResult);
        assertEquals("The characteristics of someone or something", actualDescription);
        assertEquals("Trailer Embed Link", actualTrailerEmbedLink);
        assertEquals(1, actualRank);
        assertEquals(1, actualMovieDetails.getYear());
        assertEquals(10.0f, actualRating);
        assertSame(director, actualDirector);
        assertSame(genre, actualGenre);
        assertSame(writers, actualWriters);
    }

    /**
     * Methods under test:
     * <ul>
     *   <li>
     * {@link MovieDetails#MovieDetails(String, int, String, String, String, List, float, int, String, String, String, List, List)}
     *   <li>{@link MovieDetails#setBigImage(String)}
     *   <li>{@link MovieDetails#setDescription(String)}
     *   <li>{@link MovieDetails#setDirector(List)}
     *   <li>{@link MovieDetails#setGenre(List)}
     *   <li>{@link MovieDetails#setId(String)}
     *   <li>{@link MovieDetails#setImdbLink(String)}
     *   <li>{@link MovieDetails#setImdbid(String)}
     *   <li>{@link MovieDetails#setRank(int)}
     *   <li>{@link MovieDetails#setRating(float)}
     *   <li>{@link MovieDetails#setTitle(String)}
     *   <li>{@link MovieDetails#setTrailerEmbedLink(String)}
     *   <li>{@link MovieDetails#setWriters(List)}
     *   <li>{@link MovieDetails#setYear(int)}
     *   <li>{@link MovieDetails#toString()}
     *   <li>{@link MovieDetails#getBigImage()}
     *   <li>{@link MovieDetails#getDescription()}
     *   <li>{@link MovieDetails#getDirector()}
     *   <li>{@link MovieDetails#getGenre()}
     *   <li>{@link MovieDetails#getId()}
     *   <li>{@link MovieDetails#getImdbLink()}
     *   <li>{@link MovieDetails#getImdbid()}
     *   <li>{@link MovieDetails#getRank()}
     *   <li>{@link MovieDetails#getRating()}
     *   <li>{@link MovieDetails#getTitle()}
     *   <li>{@link MovieDetails#getTrailerEmbedLink()}
     *   <li>{@link MovieDetails#getWriters()}
     *   <li>{@link MovieDetails#getYear()}
     * </ul>
     */
    @Test
    void testGettersAndSetters2() {
        // Arrange
        ArrayList<String> genre = new ArrayList<>();
        ArrayList<String> director = new ArrayList<>();

        // Act
        MovieDetails actualMovieDetails = new MovieDetails("42", 1, "Dr", "The characteristics of someone or something",
                "Big Image", genre, 10.0f, 1, "Imdb Link", "Imdbid", "Trailer Embed Link", director, new ArrayList<>());
        actualMovieDetails.setBigImage("Big Image");
        actualMovieDetails.setDescription("The characteristics of someone or something");
        ArrayList<String> director2 = new ArrayList<>();
        actualMovieDetails.setDirector(director2);
        ArrayList<String> genre2 = new ArrayList<>();
        actualMovieDetails.setGenre(genre2);
        actualMovieDetails.setId("42");
        actualMovieDetails.setImdbLink("Imdb Link");
        actualMovieDetails.setImdbid("Imdbid");
        actualMovieDetails.setRank(1);
        actualMovieDetails.setRating(10.0f);
        actualMovieDetails.setTitle("Dr");
        actualMovieDetails.setTrailerEmbedLink("Trailer Embed Link");
        ArrayList<String> writers = new ArrayList<>();
        actualMovieDetails.setWriters(writers);
        actualMovieDetails.setYear(1);
        String actualToStringResult = actualMovieDetails.toString();
        String actualBigImage = actualMovieDetails.getBigImage();
        String actualDescription = actualMovieDetails.getDescription();
        List<String> actualDirector = actualMovieDetails.getDirector();
        List<String> actualGenre = actualMovieDetails.getGenre();
        String actualId = actualMovieDetails.getId();
        String actualImdbLink = actualMovieDetails.getImdbLink();
        String actualImdbid = actualMovieDetails.getImdbid();
        int actualRank = actualMovieDetails.getRank();
        float actualRating = actualMovieDetails.getRating();
        String actualTitle = actualMovieDetails.getTitle();
        String actualTrailerEmbedLink = actualMovieDetails.getTrailerEmbedLink();
        List<String> actualWriters = actualMovieDetails.getWriters();

        // Assert that nothing has changed
        assertEquals("42", actualId);
        assertEquals("Big Image", actualBigImage);
        assertEquals("Dr", actualTitle);
        assertEquals("Imdb Link", actualImdbLink);
        assertEquals("Imdbid", actualImdbid);
        assertEquals("MovieDetails(id=42, rank=1, title=Dr, description=The characteristics of someone or something,"
                + " bigImage=Big Image, genre=[], rating=10.0, year=1, imdbLink=Imdb Link, imdbid=Imdbid, trailerEmbedLink"
                + "=Trailer Embed Link, director=[], writers=[])", actualToStringResult);
        assertEquals("The characteristics of someone or something", actualDescription);
        assertEquals("Trailer Embed Link", actualTrailerEmbedLink);
        assertEquals(1, actualRank);
        assertEquals(1, actualMovieDetails.getYear());
        assertEquals(10.0f, actualRating);
        assertEquals(genre, actualDirector);
        assertEquals(genre, actualGenre);
        assertEquals(genre, actualWriters);
        assertSame(director2, actualDirector);
        assertSame(genre2, actualGenre);
        assertSame(writers, actualWriters);
    }
}
