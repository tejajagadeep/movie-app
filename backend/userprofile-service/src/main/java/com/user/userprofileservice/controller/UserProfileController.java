package com.user.userprofileservice.controller;

import com.user.userprofileservice.dto.UserProfileDto;
import com.user.userprofileservice.exception.UnAuthorizedException;
import com.user.userprofileservice.filter.JwtService;
import com.user.userprofileservice.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/private/userProfile")
@Slf4j
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
                            schema = @Schema(implementation = UserProfileDto.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content) })
    @GetMapping("/getUserById/{username}")
    public ResponseEntity<Object> getUserProfileById(@RequestHeader("Authorization") String token,@PathVariable String username){

        if (jwtService.isTokenValid(token.substring(7),username)) {
            return new ResponseEntity<>(userProfileService.getUserProfileById(username), HttpStatus.OK);
        }
        throw new UnAuthorizedException("Un Authorized Please check user the details.");

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Update Users Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Details Updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserProfileDto.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "User Details already Exists",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content) })
    @PutMapping("/update/{username}")
    public ResponseEntity<Object> updateUserProfile(@RequestHeader("Authorization") String token,@RequestBody UserProfileDto userProfileDto, @PathVariable String username){

        if (jwtService.isTokenValid(token.substring(7),username)) {
            return new ResponseEntity<>(userProfileService.updateUserProfile(userProfileDto, username),HttpStatus.OK);
        }
        throw new UnAuthorizedException("Un Authorized Please check user the details to update.");
    }

}
