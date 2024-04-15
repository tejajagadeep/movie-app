package com.user.userprofileservice.service;

import com.user.userprofileservice.dto.User;
import com.user.userprofileservice.dto.UserProfileDto;
import com.user.userprofileservice.exception.ResourceAlreadyExistsException;
import com.user.userprofileservice.kafka.DataPublisherServiceImpl;
import com.user.userprofileservice.model.UserProfile;
import com.user.userprofileservice.repository.UserProfileRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Observed(name = "user.profile.service.impl")
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository usersProfileRepository;

    private final ModelMapper modelMapper;

    private final DataPublisherServiceImpl producer;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository usersProfileRepository, ModelMapper modelMapper, DataPublisherServiceImpl producer) {
        this.usersProfileRepository = usersProfileRepository;
        this.modelMapper = modelMapper;
        this.producer = producer;
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
    public UserProfile saveUserProfile(UserProfileDto userProfileDto) {

        User user =new User();
        user.setUsername(userProfileDto.getUsername());
        user.setPassword(userProfileDto.getPassword());
        user.setRole("MEMBER");
        log.info("------"+ user +"--------");
        try {
            producer.sendMessage(user);
        } catch (KafkaException ex){
            log.error(ex.getMessage());

        }

        if (usersProfileRepository.existsById(userProfileDto.getUsername())) {
            throw new ResourceAlreadyExistsException("Username Already exists");
        }

        if (usersProfileRepository.existsByEmail(userProfileDto.getEmail())) {
            throw new ResourceAlreadyExistsException("Email Already exists");
        }

        return usersProfileRepository.save(modelMapper.map(userProfileDto,UserProfile.class));
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
