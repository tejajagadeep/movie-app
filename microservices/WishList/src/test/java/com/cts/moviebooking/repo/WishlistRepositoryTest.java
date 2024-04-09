package com.cts.moviebooking.repo;

import com.cts.moviebooking.entity.WishListEntity;
import com.cts.moviebooking.repo.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataMongoTest
class WishlistRepositoryTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishListEntity wishListEntity;

    @Test
    void findByUsername_validUsername_shouldReturnWishlistEntity() {
        String username = "testUser";
        when(wishlistRepository.findByUsername(username)).thenReturn(Optional.of(wishListEntity));

        Optional<WishListEntity> result = wishlistRepository.findByUsername(username);

        assertEquals(Optional.of(wishListEntity), result);
        verify(wishlistRepository, times(1)).findByUsername(username);
    }

    @Test
    void findByUsername_invalidUsername_shouldReturnEmptyOptional() {
        String username = "nonExistingUser";
        when(wishlistRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<WishListEntity> result = wishlistRepository.findByUsername(username);

        assertEquals(Optional.empty(), result);
        verify(wishlistRepository, times(1)).findByUsername(username);
    }
}
