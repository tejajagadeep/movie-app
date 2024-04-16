package com.cts.moviebooking.serviceimpl;

import com.cts.moviebooking.dto.MovieDto;
import com.cts.moviebooking.entity.WishListEntity;
import com.cts.moviebooking.exception.NotFoundException;
import com.cts.moviebooking.exception.WishlistException;
import com.cts.moviebooking.repo.WishlistRepository;
import com.cts.moviebooking.service.WishListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishListService {

    private static final Logger logger = LoggerFactory.getLogger(WishlistServiceImpl.class);

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public Optional<WishListEntity> getWishlistByUsername(String username) {
        logger.info("Fetching wishlist for username: {}", username);
        return wishlistRepository.findByUsername(username);
    }

    @Override
    public WishListEntity addToWishlist(String username, MovieDto movieDto) {
        try {
            Optional<WishListEntity> existingWishlist = wishlistRepository.findByUsername(username);

            WishListEntity wishlist = existingWishlist.orElse(new WishListEntity());
            wishlist.setUsername(username);

            List<MovieDto> movies = wishlist.getMovies();
            if (movies == null) {
                movies = new ArrayList<>();
            }

            // Check if the movie with the same title already exists
            boolean movieExists = movies.stream()
                    .anyMatch(existingMovie -> existingMovie.getTitle().equals(movieDto.getTitle()));

            if (!movieExists) {
                movies.add(movieDto);
                wishlist.setMovies(movies);

                logger.info("Added movie to wishlist. Username: {}, Movie: {}", username, movieDto.getTitle());

                return wishlistRepository.save(wishlist);
            } else {
                logger.warn("Movie with title {} already exists in the wishlist for username: {}", movieDto.getTitle(), username);
                return wishlist; // Movie already exists, return the unchanged wishlist
            }
        } catch (Exception e) {
            logger.error("Error adding to wishlist for username: {}", username, e);
            throw new WishlistException("Error adding to wishlist");
        }
    }


    @Override
    public WishListEntity removeFromWishlist(String username, MovieDto movieDto) {
        try {
            Optional<WishListEntity> existingWishlist = wishlistRepository.findByUsername(username);

            WishListEntity wishlist = existingWishlist.orElseThrow(() -> new NotFoundException("Wishlist not found"));
            List<MovieDto> movies = wishlist.getMovies();

            if (movies != null) {
                movies.removeIf(movie -> movie.getTitle().equals(movieDto.getTitle()));
                wishlist.setMovies(movies);  // Update the movies list in the WishListEntity
                wishlistRepository.save(wishlist);  // Save the updated WishListEntity to the database
                logger.info("Removed movie from wishlist. Username: {}, Movie: {}", username, movieDto.getTitle());
            }

            return wishlist;
        } catch (Exception e) {
            logger.error("Error removing from wishlist for username: {}", username, e);
            throw new WishlistException("Error removing from wishlist");
        }
    }

    @Override
    public void clearWishlist(String username) {
        try {
            wishlistRepository.findByUsername(username)
                    .ifPresent(wishlist -> {
                        wishlist.setMovies(null);
                        wishlistRepository.save(wishlist);
                        logger.info("Cleared wishlist for username: {}", username);
                    });
        } catch (Exception e) {
            logger.error("Error clearing wishlist for username: {}", username, e);
            throw new WishlistException("Error clearing wishlist");
        }
    }
}
