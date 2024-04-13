package com.cts.wishlistservice.service;

import com.cts.wishlistservice.dto.MovieDto;
import com.cts.wishlistservice.dto.WishlistDto;
import com.cts.wishlistservice.exception.ResourceNotFoundException;
import com.cts.wishlistservice.exception.UnAuthorizedException;
import com.cts.wishlistservice.feign.AuthenticationClient;
import com.cts.wishlistservice.model.Movie;
import com.cts.wishlistservice.model.Wishlist;
import com.cts.wishlistservice.repository.WishlistRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationClient authenticationClient;

    @Autowired
    public WishlistServiceImpl(WishlistRepository wishlistRepository, ModelMapper modelMapper, AuthenticationClient authenticationClient) {
        this.wishlistRepository = wishlistRepository;
        this.modelMapper = modelMapper;
        this.authenticationClient = authenticationClient;
    }

    /**
     * Get All wish list of a user
     * @param token
     * @param username
     * @return Wishlist
     */
    @Override
    public WishlistDto getWishlists(String token, String username) {
        Map<String,String> info= authenticationClient.validateToken(token).getBody();
//        if(info==null || info.containsKey(username)) {
            return modelMapper.map(wishlistRepository.findById(username).orElseThrow(()->new ResourceNotFoundException("Username "+username+" not found.")), WishlistDto.class);
//        }

//        throw new UnAuthorizedException("Un Authorized Please check user the details.");
    }

    /**
     * Delete movie by id for user
     * @param token
     * @param username
     * @param id
     * @return Wishlist
     */
    @Override
    public WishlistDto deleteWishlist(String token, String username, String id) {
        Map<String,String> info= authenticationClient.validateToken(token).getBody();
        if(info==null || !info.containsKey(username) ) {
            throw new UnAuthorizedException("Un Authorized Please check user the details.");
        }
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
     * @param token
     * @param username
     * @param movieDtp
     * @return WishlistDto
     */
    @Override
    public WishlistDto addWishlist(String token, String username, MovieDto movieDtp) {
        Map<String,String> info= authenticationClient.validateToken(token).getBody();
        if(info==null || !info.containsKey(username)) {
            throw new UnAuthorizedException("Un Authorized Please check user the details.");
        }
        Optional<Wishlist> wishListOptional = wishlistRepository.findById(username);
        Movie movie = modelMapper.map(movieDtp, Movie.class);
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
     * @param wishList
     * @param movie
     */
    private void addOrUpdateTrack(Wishlist wishList, Movie movie) {
        // Check if the track with the same ID already exists in the wish list
        boolean trackExists = wishList.getMovies().stream()
                .anyMatch(track -> track.getId().equals(movie.getId()));

        if (!trackExists) {
            // Track doesn't exist, add it to the wish list
            wishList.getMovies().add(movie);
        } else {
            // Track already exists, update it (if needed)
            wishList.getMovies().stream()
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
