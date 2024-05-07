package com.user.userprofileservice.service;

import com.user.userprofileservice.dto.User;
import com.user.userprofileservice.dto.UserProfileDto;
import com.user.userprofileservice.dto.UserProfileUpdateDto;
import com.user.userprofileservice.exception.ResourceAlreadyExistsException;
import com.user.userprofileservice.exception.ResourceNotFoundException;
import com.user.userprofileservice.kafka.DataPublisherServiceImpl;
import com.user.userprofileservice.model.UserProfile;
import com.user.userprofileservice.repository.UserProfileRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.KafkaException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Observed(name = "user.profile.service.impl")
@Log4j2
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
    public UserProfile getUserProfileById(String username) {
        return  usersProfileRepository.findById(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Entity not found with ID: " + username));
    }

    @Override
    @Observed(name = "save.user.profile")
    public UserProfile saveUserProfile(UserProfileDto userProfileDto) {

        if (usersProfileRepository.existsById(userProfileDto.getUsername())) {
            throw new ResourceAlreadyExistsException("Username Already exists");
        }

        if (usersProfileRepository.existsByEmail(userProfileDto.getEmail())) {
            throw new ResourceAlreadyExistsException("Email Already exists");
        }

        User user =new User();
        user.setUsername(userProfileDto.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(userProfileDto.getPassword()));
        user.setRole("MEMBER");
        log.info("------"+ user +"--------");
        UserProfile userProfile = null;
        try {
            producer.sendMessage(user);
            userProfile = usersProfileRepository.save(modelMapper.map(userProfileDto,UserProfile.class));
        } catch (KafkaException ex){
            log.error(ex.getMessage());
        }
        return userProfile;
    }

    @Override
    @Observed(name = "update.user.profile")
    public UserProfile updateUserProfile(UserProfileUpdateDto userProfileDto, String username) {
        UserProfile userProfile = usersProfileRepository.findById(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Entity not found with ID: " + username)
                       );
        if (!userProfile.getEmail().equals(userProfileDto.getEmail()) && usersProfileRepository.existsByEmail(userProfileDto.getEmail())) {
            throw new ResourceAlreadyExistsException("Email Already exists");
        }
        userProfile.setFirstName(userProfileDto.getFirstName());
        userProfile.setLastName(userProfileDto.getLastName());

        userProfile.setEmail(userProfileDto.getEmail());
        userProfile.setPhoneNumber(userProfileDto.getPhoneNumber());
        return usersProfileRepository.save(userProfile);
    }

}
