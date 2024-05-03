package com.spotify.userprofile.service;

import com.spotify.userprofile.dto.UserProfileDto;
import com.spotify.userprofile.exception.ResourceAlreadyExistsException;
import com.spotify.userprofile.model.UserProfile;
import com.spotify.userprofile.repository.UserProfileRepository;
import io.micrometer.observation.annotation.Observed;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@Observed(name = "user.profile.service.impl")
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository usersProfileRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository usersProfileRepository, ModelMapper modelMapper) {
        this.usersProfileRepository = usersProfileRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    @Observed(name = "get.all.users")
    public List<UserProfileDto> getAllUsers() {

        return Stream.of(usersProfileRepository.findAll())
                .flatMap(entityList -> entityList.stream()
                        .map(entity -> modelMapper.map(entity, UserProfileDto.class))).toList();

    }

    @Override
    @Observed(name = "get.user.profile.by.id")
    public UserProfileDto getUserProfileById(String username) {
        UserProfile entity = usersProfileRepository.findById(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Entity not found with ID: " + username));

        return modelMapper.map(entity, UserProfileDto.class);
    }

    @Override
    @Observed(name = "save.user.profile")
    public UserProfileDto saveUserProfile(UserProfile userProfile) {


        if (usersProfileRepository.existsById(userProfile.getUsername())) {
            throw new ResourceAlreadyExistsException("Username Already exists");
        }

        if (usersProfileRepository.existsByEmail(userProfile.getEmail())) {
            throw new ResourceAlreadyExistsException("Email Already exists");
        }

        return modelMapper.map(usersProfileRepository.save(userProfile), UserProfileDto.class);
    }

    @Override
    @Observed(name = "update.user.profile")
    public UserProfileDto updateUserProfile(UserProfileDto userProfileDto, String username) {
        UserProfile userProfile = usersProfileRepository.findById(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Entity not found with ID: " + username)
                       );

        userProfile.setFirstName(userProfileDto.getFirstName());
        userProfile.setLastName(userProfileDto.getLastName());
        userProfile.setDateOfBirth(userProfileDto.getDateOfBirth());

        usersProfileRepository.save(userProfile);

        return modelMapper.map(userProfile, UserProfileDto.class);
    }

}
