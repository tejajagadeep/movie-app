package com.cts.moviebooking.repo;

import com.cts.moviebooking.entity.WishListEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WishlistRepository extends MongoRepository<WishListEntity, String> {

    Optional<WishListEntity> findByUsername(String username);
}
