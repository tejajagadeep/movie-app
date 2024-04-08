package com.spotify.userProfile.services;

import com.spotify.userProfile.Exceptions.UserAlreadyExistsException;
import com.spotify.userProfile.models.User;

public interface UserService {
    public User saveUser(User user) throws UserAlreadyExistsException;
}
