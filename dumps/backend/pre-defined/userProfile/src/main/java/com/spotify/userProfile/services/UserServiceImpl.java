package com.spotify.userProfile.services;

import com.spotify.userProfile.Exceptions.UserAlreadyExistsException;
import com.spotify.userProfile.models.User;
import com.spotify.userProfile.models.AuthRequest;
import com.spotify.userProfile.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;
    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        log.info("saveUser service executing!");
        Optional<User> user1 = userRepository.findById(user.getUserId());
        if(!user1.isEmpty()){
            log.error("UserAlreadyExistsException Occurred!!");
            throw new UserAlreadyExistsException("User Already Exists!");
        }
        AuthRequest authRequest = new AuthRequest(user.getUsername(), user.getPassword());
        //kafkaMessageProducer.sendMessage(authRequest);
        log.info("Message sent to queue");
        return userRepository.save(user);
    }
}
