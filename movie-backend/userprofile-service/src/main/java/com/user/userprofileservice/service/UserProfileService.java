package com.user.userprofileservice.service;




import com.user.userprofileservice.dto.UserProfileDto;
import com.user.userprofileservice.dto.UserProfileUpdateDto;
import com.user.userprofileservice.model.UserProfile;

public interface UserProfileService {

    UserProfile getUserProfileById(String username);

    UserProfile saveUserProfile(UserProfileDto userProfileDto);

    UserProfile updateUserProfile(UserProfileUpdateDto userProfileDto, String username);

}
