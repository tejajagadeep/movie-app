package com.spotify.authentication.services;

import com.spotify.authentication.exceptions.UserAlreadyExistsException;
import com.spotify.authentication.models.UserInfo;
import com.spotify.authentication.repositories.UserCredentialsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Slf4j
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserCredentialsRepository userCredentialsRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername service executed");
        Optional<UserInfo> userDetail = userCredentialsRepository.findByUsername(username);
        return userDetail.map(UserInfoDetails::new).orElseThrow(()->new UsernameNotFoundException("Username not found: "+username));
    }
    public UserInfo adduser(UserInfo userInfo) throws UserAlreadyExistsException {
        log.info("addUser service executed!");
        Optional<UserInfo> userInfoOptional = userCredentialsRepository.findByUsername(userInfo.getUsername());
        if(!userInfoOptional.isEmpty()){
            log.info("UserAlreadyExistsException occurred in addUser service");
            throw new UserAlreadyExistsException("Error: User with username: "+userInfo.getUsername()+" already exist!");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userCredentialsRepository.save(userInfo);
        log.info("User credentials saved");
        return userInfo;
    }
    public UserInfo getUser(String username){
        log.info("getUser service executed!");
        return userCredentialsRepository.findByUsername(username).get();
    }
}
