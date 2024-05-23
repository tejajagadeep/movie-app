package com.user.userprofileservice.controller;

import com.user.userprofileservice.dto.UserProfileDto;
import com.user.userprofileservice.dto.UserProfileUpdateDto;
import com.user.userprofileservice.exception.ErrorResponse;
import com.user.userprofileservice.exception.UnAuthorizedException;
import com.user.userprofileservice.filter.JwtService;
import com.user.userprofileservice.model.UserProfile;
import com.user.userprofileservice.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/private/userProfile")
@Log4j2
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final JwtService jwtService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService, JwtService jwtService) {
        this.userProfileService = userProfileService;
        this.jwtService = jwtService;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get Users Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Details Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserProfile.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) ) ,
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) ) })
    @GetMapping("/getUserById/{username}")
    public ResponseEntity<Object> getUserProfileById(@Parameter(hidden = true) @RequestHeader("Authorization") String token, @PathVariable String username){

        if (jwtService.isTokenValid(token,username)) {
            return new ResponseEntity<>(userProfileService.getUserProfileById(username), HttpStatus.OK);
        }
        throw new UnAuthorizedException("Unauthorized Please check user details.");

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Update Users Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Details Updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserProfile.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) ),
            @ApiResponse(responseCode = "409", description = "Username/Email already Exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) ),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) ) })
    @PutMapping("/update/{username}")
    public ResponseEntity<Object> updateUserProfile(@Parameter(hidden = true) @RequestHeader("Authorization") String token, @Valid @RequestBody UserProfileUpdateDto userProfileDto, @PathVariable String username){

        if (jwtService.isTokenValid(token,username)) {
            return new ResponseEntity<>(userProfileService.updateUserProfile(userProfileDto, username),HttpStatus.OK);
        }
        throw new UnAuthorizedException("Unauthorized Please check the user details to update.");
    }

}
