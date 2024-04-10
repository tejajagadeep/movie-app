package com.cts.wishlistservice.repository;

import com.cts.wishlistservice.model.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String> {

    @Query("{'username': ?0, 'movies.id': ?1}")
    void deleteMovieFromWishlist(String username, String movieId);
}
