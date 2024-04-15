package com.cts.wishlistservice.service;

import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;
import com.cts.wishlistservice.exception.ResourceNotFoundException;
import com.cts.wishlistservice.model.Movie;
import com.cts.wishlistservice.model.Wishlist;
import com.cts.wishlistservice.repository.WishlistRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public WishlistServiceImpl(WishlistRepository wishlistRepository, ModelMapper modelMapper) {
        this.wishlistRepository = wishlistRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Get All wish list of a user
     * @param username = username to access wishlist
     * @return Wishlist
     */
    @Override
    @Observed(name = "get.wishlists")
    public WishlistDto getWishlists(String username) {
            return modelMapper.map(wishlistRepository.findById(username)
                    .orElseThrow(
                            ()->new ResourceNotFoundException("Username not found.")
                    ), WishlistDto.class);
    }

    /**
     * Delete movie by id for user
     * @param username = username to access wishlist
     * @param id = movie id
     * @return Wishlist
     */
    @Override
    @Observed(name = "delete.wishlist")
    public WishlistDto deleteWishlist(String username, String id) {

        Wishlist wishlist = wishlistRepository.findById(username).orElseThrow(()->new ResourceNotFoundException("Username "+username+" not found."));

        // Get the list of movies from the wishlist
        List<Movie> movies = wishlist.getMovies();

        // Iterate over the list of movies to find the movie with the specified ID
        movies.removeIf(movie -> movie.getId().equals(id));

        // Update the wishlist in the repository
        wishlistRepository.save(wishlist);

        return modelMapper.map(wishlistRepository.findById(username).orElseThrow(()->new ResourceNotFoundException("Username "+username+" not found.")), WishlistDto.class);
    }

    /**
     *Add favorite movie to wishlist filtered by username
     * @param username = username to access wishlist
     * @param movieDto = movie details to be added
     * @return WishlistDto
     */
    @Override
    @Observed(name = "add.wishlist")
    public WishlistDto addWishlist(String username, MovieDto movieDto) {

        Optional<Wishlist> wishListOptional = wishlistRepository.findById(username);
        Movie movie = modelMapper.map(movieDto, Movie.class);
        Wishlist wishlist;
        if (wishListOptional.isPresent()) {
            // User's wish list exists, add or update the track
            wishlist = wishListOptional.get();
            addOrUpdateTrack(wishlist, movie);
        } else {
            // User's wish list doesn't exist, create a new wish list
            wishlist = new Wishlist();
            wishlist.setUsername(username);
            wishlist.setMovies(List.of(movie));
        }
        return modelMapper.map(wishlistRepository.save(wishlist), WishlistDto.class);
    }

    /**
     * if movie already exists update or add to wishlist
     * @param wishlist = get wishlist of the user
     * @param movie = movie details
     */
    private void addOrUpdateTrack(Wishlist wishlist, Movie movie) {
        // Check if the track with the same ID already exists in the wish list
        boolean trackExists = wishlist.getMovies().stream()
                .anyMatch(track -> track.getId().equals(movie.getId()));

        if (!trackExists) {
            // Track doesn't exist, add it to the wish list
            wishlist.getMovies().add(movie);
        } else {
            // Track already exists, update it (if needed)
            wishlist.getMovies().stream()
                    .filter(m -> m.getId().equals(movie.getId()))
                    .findFirst()
                    .ifPresent(existingMovie -> {
                        existingMovie.setId(movie.getId());
                        existingMovie.setRank(movie.getRank());
                        existingMovie.setTitle(movie.getTitle());
                        existingMovie.setBigImage(movie.getBigImage());
                        existingMovie.setGenre(movie.getGenre());
                        existingMovie.setRating(movie.getRating());
                        existingMovie.setYear(movie.getYear());
                        existingMovie.setImdbLink(movie.getImdbLink());
                    });
        }
    }

}
