package com.cts.moviebooking.repository;

import com.cts.moviebooking.entity.UserEntity;
import com.cts.moviebooking.repo.UserRepo;
import com.cts.moviebooking.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepoTest {

    @Mock
    private UserRepo userRepo;

    @Test
    public void testFindByEmail() {
        String userEmail = "test@example.com";
        UserEntity user = new UserEntity();
        user.setEmail(userEmail);

        when(userRepo.findByEmail(userEmail)).thenReturn(user);

        UserEntity result = userRepo.findByEmail(userEmail);

        verify(userRepo, times(1)).findByEmail(userEmail);
        assertNotNull(result);
        assertEquals(userEmail, result.getEmail());
    }

    @Test
    public void testFindByUserName() {
        String userName = "testUser";
        UserEntity user = new UserEntity();
        user.setUserName(userName);

        when(userRepo.findByUserName(userName)).thenReturn(user);

        UserEntity result = userRepo.findByUserName(userName);

        verify(userRepo, times(1)).findByUserName(userName);
        assertNotNull(result);
        assertEquals(userName, result.getUserName());
    }

    @Test
    public void testExistsByUserName() {
        String userName = "existingUser";

        when(userRepo.existsByUserName(userName)).thenReturn(true);

        assertTrue(userRepo.existsByUserName(userName));

        verify(userRepo, times(1)).existsByUserName(userName);
    }

    @Test
    public void testExistsByEmail() {
        String userEmail = "existing@example.com";

        when(userRepo.existsByEmail(userEmail)).thenReturn(true);

        assertTrue(userRepo.existsByEmail(userEmail));

        verify(userRepo, times(1)).existsByEmail(userEmail);
    }

    @Test
    public void testFindByUserNameAndPassword() {
        String userName = "testUser";
        String password = "testPassword";

        UserEntity user = new UserEntity();
        user.setUserName(userName);
        user.setPassword(password);

        when(userRepo.findByUserNameAndPassword(userName, password)).thenReturn(user);

        UserEntity result = userRepo.findByUserNameAndPassword(userName, password);

        verify(userRepo, times(1)).findByUserNameAndPassword(userName, password);
        assertNotNull(result);
        assertEquals(userName, result.getUserName());
        assertEquals(password, result.getPassword());
    }


}
