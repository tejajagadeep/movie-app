package com.user.userprofileservice.controller;

import com.user.userprofileservice.dto.UserProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("call/consumer")
@Slf4j
public class ConsumerController
{
    @Operation(summary = "login User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserProfileDto.class)) }),
            @ApiResponse(responseCode = "404", description = "user not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content) })
    @PostMapping(value="/login")
    public ResponseEntity<Object> consumeLogin(@RequestBody UserProfileDto userDto)
    {
        String baseUrl ="http://localhost:8083/auth/v1/login";

        log.info("{}----", userDto);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map<String,String>> result;
        try
        {

            result=restTemplate.exchange(baseUrl, HttpMethod.POST, getHeaders(userDto), new ParameterizedTypeReference<>(){});
           log.info(Objects.requireNonNull(result.getBody()).toString());
        }
        catch(Exception e)
        {
            return new ResponseEntity<>("Login was not successful" , HttpStatus.UNAUTHORIZED);

        }
        return new ResponseEntity<>(result.getBody(), HttpStatus.OK);

    }




    private static HttpEntity<UserProfileDto> getHeaders(UserProfileDto userDto)
    {
        HttpHeaders header = new HttpHeaders();

        header.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        header.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(userDto, header);
    }

}





