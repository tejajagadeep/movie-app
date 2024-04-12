package com.user.userprofileservice.service;




import com.user.userprofileservice.dto.UserProfileDto;
import com.user.userprofileservice.model.UserProfile;

import java.util.List;

public interface UserProfileService {
    List<UserProfileDto> getAllUsers();

    UserProfileDto getUserProfileById(String username);

    UserProfile saveUserProfile(UserProfileDto userProfileDto);

    UserProfileDto updateUserProfile(UserProfileDto userProfileDto, String username);

}
