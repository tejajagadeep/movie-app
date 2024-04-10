package com.auth.authenticationservice.repository;

import com.auth.authenticationservice.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserDetails,String> {

//    @Query(value="select u from UserDetails u where u.username= :username and u.password= :password")
//    public UserDetails validateUser(String username, String password);//login
//
//    @Query(value="select u.role from UserDetails u where u.username=:username and u.password=:password")
//    public String GetRole(String username, String password);
    UserDetails findByUsername(String username);
}
