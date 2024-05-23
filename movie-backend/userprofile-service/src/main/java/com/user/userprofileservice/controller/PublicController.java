package com.user.userprofileservice.controller;

import com.user.userprofileservice.dto.UserProfileDto;
import com.user.userprofileservice.exception.ErrorResponse;
import com.user.userprofileservice.model.UserProfile;
import com.user.userprofileservice.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/public/userProfile")
public class PublicController {

    private final UserProfileService userProfileService;

    @Autowired
    public PublicController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Operation(summary = "Save User's Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Details Saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserProfile.class)) }),
            @ApiResponse(responseCode = "409", description = "User Details already Exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) ) })
    @PostMapping("/addUser")
    public ResponseEntity<Object> saveUserProfile(@Valid @RequestBody UserProfileDto userProfileDto){
        return new ResponseEntity<>(userProfileService.saveUserProfile(userProfileDto), HttpStatus.CREATED);
    }

}
