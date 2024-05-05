package com.cts.wishlistservice.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyFloat;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;
import com.cts.wishlistservice.exception.ResourceNotFoundException;
import com.cts.wishlistservice.model.Movie;
import com.cts.wishlistservice.model.Wishlist;
import com.cts.wishlistservice.repository.WishlistRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WishlistServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class WishlistServiceImplJunitTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private WishlistRepository wishlistRepository;

    @Autowired
    private WishlistServiceImpl wishlistServiceImpl;

    /**
     * Method under test: {@link WishlistServiceImpl#getWishlists(String)}
     */
    @Test
    void testGetWishlists() {
        // Arrange
        Wishlist wishlist = new Wishlist();
        wishlist.setMovies(new ArrayList<>());
        wishlist.setUsername("janedoe");
        Optional<Wishlist> ofResult = Optional.of(wishlist);
        when(wishlistRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        WishlistDto wishlistDto = new WishlistDto();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<WishlistDto>>any())).thenReturn(wishlistDto);

        // Act
        WishlistDto actualWishlists = wishlistServiceImpl.getWishlists("janedoe");

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(wishlistRepository).findById(eq("janedoe"));
        assertSame(wishlistDto, actualWishlists);
    }

    /**
     * Method under test: {@link WishlistServiceImpl#getWishlists(String)}
     */
    @Test
    void testGetWishlists2() {
        // Arrange
        Wishlist wishlist = new Wishlist();
        wishlist.setMovies(new ArrayList<>());
        wishlist.setUsername("janedoe");
        Optional<Wishlist> ofResult = Optional.of(wishlist);
        when(wishlistRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<WishlistDto>>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> wishlistServiceImpl.getWishlists("janedoe"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(wishlistRepository).findById(eq("janedoe"));
    }



    /**
     * Method under test: {@link WishlistServiceImpl#deleteWishlist(String, String)}
     */
    @Test
    void testDeleteWishlist() {
        // Arrange
        Wishlist wishlist = new Wishlist();
        wishlist.setMovies(new ArrayList<>());
        wishlist.setUsername("janedoe");
        Optional<Wishlist> ofResult = Optional.of(wishlist);

        Wishlist wishlist2 = new Wishlist();
        wishlist2.setMovies(new ArrayList<>());
        wishlist2.setUsername("janedoe");
        when(wishlistRepository.save(Mockito.<Wishlist>any())).thenReturn(wishlist2);
        when(wishlistRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        WishlistDto wishlistDto = new WishlistDto();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<WishlistDto>>any())).thenReturn(wishlistDto);

        // Act
        WishlistDto actualDeleteWishlistResult = wishlistServiceImpl.deleteWishlist("janedoe", "42");

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(wishlistRepository, atLeast(1)).findById(eq("janedoe"));
        verify(wishlistRepository).save(isA(Wishlist.class));
        assertSame(wishlistDto, actualDeleteWishlistResult);
    }

    /**
     * Method under test: {@link WishlistServiceImpl#deleteWishlist(String, String)}
     */
    @Test
    void testDeleteWishlist2() {
        // Arrange
        Wishlist wishlist = new Wishlist();
        wishlist.setMovies(new ArrayList<>());
        wishlist.setUsername("janedoe");
        Optional<Wishlist> ofResult = Optional.of(wishlist);

        Wishlist wishlist2 = new Wishlist();
        wishlist2.setMovies(new ArrayList<>());
        wishlist2.setUsername("janedoe");
        when(wishlistRepository.save(Mockito.<Wishlist>any())).thenReturn(wishlist2);
        when(wishlistRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<WishlistDto>>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> wishlistServiceImpl.deleteWishlist("janedoe", "42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(wishlistRepository, atLeast(1)).findById(eq("janedoe"));
        verify(wishlistRepository).save(isA(Wishlist.class));
    }

    /**
     * Method under test: {@link WishlistServiceImpl#deleteWishlist(String, String)}
     */
    @Test
    void testDeleteWishlist3() {
        // Arrange
        Movie movie = new Movie();
        movie.setDescription("The characteristics of someone or something");
        movie.setGenre(new ArrayList<>());
        movie.setImage("Image");
        movie.setImdbLink("Imdb Link");
        movie.setImdbid("Imdbid");
        movie.setRating(10.0f);
        movie.setTitle("Dr");
        movie.setYear(1);

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(movie);

        Wishlist wishlist = new Wishlist();
        wishlist.setMovies(movies);
        wishlist.setUsername("janedoe");
        Optional<Wishlist> ofResult = Optional.of(wishlist);

        Wishlist wishlist2 = new Wishlist();
        wishlist2.setMovies(new ArrayList<>());
        wishlist2.setUsername("janedoe");
        when(wishlistRepository.save(Mockito.<Wishlist>any())).thenReturn(wishlist2);
        when(wishlistRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        WishlistDto wishlistDto = new WishlistDto();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<WishlistDto>>any())).thenReturn(wishlistDto);

        // Act
        WishlistDto actualDeleteWishlistResult = wishlistServiceImpl.deleteWishlist("janedoe", "42");

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(wishlistRepository, atLeast(1)).findById(eq("janedoe"));
        verify(wishlistRepository).save(isA(Wishlist.class));
        assertSame(wishlistDto, actualDeleteWishlistResult);
    }

    /**
     * Method under test: {@link WishlistServiceImpl#deleteWishlist(String, String)}
     */
    @Test
    void testDeleteWishlist4() {
        // Arrange
        Movie movie = new Movie();
        movie.setDescription("The characteristics of someone or something");
        movie.setGenre(new ArrayList<>());
        movie.setImage("Image");
        movie.setImdbLink("Imdb Link");
        movie.setImdbid("Imdbid");
        movie.setRating(10.0f);
        movie.setTitle("Dr");
        movie.setYear(1);

        Movie movie2 = new Movie();
        movie2.setDescription("Imdbid");
        movie2.setGenre(new ArrayList<>());
        movie2.setImage("42");
        movie2.setImdbLink("42");
        movie2.setImdbid("42");
        movie2.setRating(0.5f);
        movie2.setTitle("Mr");
        movie2.setYear(1);

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(movie2);
        movies.add(movie);

        Wishlist wishlist = new Wishlist();
        wishlist.setMovies(movies);
        wishlist.setUsername("janedoe");
        Optional<Wishlist> ofResult = Optional.of(wishlist);

        Wishlist wishlist2 = new Wishlist();
        wishlist2.setMovies(new ArrayList<>());
        wishlist2.setUsername("janedoe");
        when(wishlistRepository.save(Mockito.<Wishlist>any())).thenReturn(wishlist2);
        when(wishlistRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        WishlistDto wishlistDto = new WishlistDto();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<WishlistDto>>any())).thenReturn(wishlistDto);

        // Act
        WishlistDto actualDeleteWishlistResult = wishlistServiceImpl.deleteWishlist("janedoe", "42");

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(wishlistRepository, atLeast(1)).findById(eq("janedoe"));
        verify(wishlistRepository).save(isA(Wishlist.class));
        assertSame(wishlistDto, actualDeleteWishlistResult);
    }

    /**
     * Method under test: {@link WishlistServiceImpl#addWishlist(String, MovieDto)}
     */
    @Test
    void testAddWishlist() {
        // Arrange
        Wishlist wishlist = new Wishlist();
        wishlist.setMovies(new ArrayList<>());
        wishlist.setUsername("janedoe");
        Optional<Wishlist> ofResult = Optional.of(wishlist);
        when(wishlistRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> wishlistServiceImpl.addWishlist("janedoe", new MovieDto()));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(wishlistRepository).findById(eq("janedoe"));
    }

    /**
     * Method under test: {@link WishlistServiceImpl#addWishlist(String, MovieDto)}
     */
    @Test
    void testAddWishlist2() {
        // Arrange
        Movie movie = mock(Movie.class);
        when(movie.getImdbid()).thenReturn("Imdbid");
        doNothing().when(movie).setDescription(Mockito.<String>any());
        doNothing().when(movie).setGenre(Mockito.<List<String>>any());
        doNothing().when(movie).setImage(Mockito.<String>any());
        doNothing().when(movie).setImdbLink(Mockito.<String>any());
        doNothing().when(movie).setImdbid(Mockito.<String>any());
        doNothing().when(movie).setRating(anyFloat());
        doNothing().when(movie).setTitle(Mockito.<String>any());
        doNothing().when(movie).setYear(anyInt());
        movie.setDescription("Description");
        movie.setGenre(new ArrayList<>());
        movie.setImage("42");
        movie.setImdbLink("42");
        movie.setImdbid("42");
        movie.setRating(0.5f);
        movie.setTitle("Mr");
        movie.setYear(0);

        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        Wishlist wishlist = mock(Wishlist.class);
        when(wishlist.getMovies()).thenReturn(movieList);
        doNothing().when(wishlist).setMovies(Mockito.<List<Movie>>any());
        doNothing().when(wishlist).setUsername(Mockito.<String>any());
        wishlist.setMovies(new ArrayList<>());
        wishlist.setUsername("janedoe");
        Optional<Wishlist> ofResult = Optional.of(wishlist);
        when(wishlistRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        Wishlist wishlist2 = mock(Wishlist.class);
        doNothing().when(wishlist2).setMovies(Mockito.<List<Movie>>any());
        doNothing().when(wishlist2).setUsername(Mockito.<String>any());
        wishlist2.setMovies(new ArrayList<>());
        wishlist2.setUsername("janedoe");
        Movie movie2 = mock(Movie.class);
        when(movie2.getTitle()).thenThrow(new ResourceNotFoundException("An error occurred"));
        when(movie2.getImdbid()).thenReturn("Imdbid");
        doNothing().when(movie2).setDescription(Mockito.<String>any());
        doNothing().when(movie2).setGenre(Mockito.<List<String>>any());
        doNothing().when(movie2).setImage(Mockito.<String>any());
        doNothing().when(movie2).setImdbLink(Mockito.<String>any());
        doNothing().when(movie2).setImdbid(Mockito.<String>any());
        doNothing().when(movie2).setRating(anyFloat());
        doNothing().when(movie2).setTitle(Mockito.<String>any());
        doNothing().when(movie2).setYear(anyInt());
        movie2.setDescription("The characteristics of someone or something");
        movie2.setGenre(new ArrayList<>());
        movie2.setImage("Image");
        movie2.setImdbLink("Imdb Link");
        movie2.setImdbid("Imdbid");
        movie2.setRating(10.0f);
        movie2.setTitle("Dr");
        movie2.setYear(1);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(movie2);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> wishlistServiceImpl.addWishlist("janedoe", new MovieDto()));
        verify(movie, atLeast(1)).getImdbid();
        verify(movie2, atLeast(1)).getImdbid();
        verify(movie2).getTitle();
        verify(movie).setDescription(eq("Description"));
        verify(movie2).setDescription(eq("The characteristics of someone or something"));
        verify(movie).setGenre(isA(List.class));
        verify(movie2).setGenre(isA(List.class));
        verify(movie).setImage(eq("42"));
        verify(movie2).setImage(eq("Image"));
        verify(movie).setImdbLink(eq("42"));
        verify(movie2).setImdbLink(eq("Imdb Link"));
        verify(movie, atLeast(1)).setImdbid(Mockito.<String>any());
        verify(movie2).setImdbid(eq("Imdbid"));
        verify(movie).setRating(eq(0.5f));
        verify(movie2).setRating(eq(10.0f));
        verify(movie2).setTitle(eq("Dr"));
        verify(movie).setTitle(eq("Mr"));
        verify(movie).setYear(eq(0));
        verify(movie2).setYear(eq(1));
        verify(wishlist, atLeast(1)).getMovies();
        verify(wishlist2).setMovies(isA(List.class));
        verify(wishlist).setMovies(isA(List.class));
        verify(wishlist2).setUsername(eq("janedoe"));
        verify(wishlist).setUsername(eq("janedoe"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(wishlistRepository).findById(eq("janedoe"));
    }
}
