package com.cts.moviebooking.repo;

import com.cts.moviebooking.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
    UserEntity findByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    UserEntity findByUserNameAndPassword(String userName, String password);
}