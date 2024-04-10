package com.auth.authenticationservice.controller;


import com.auth.authenticationservice.dto.AuthAccessToken;
import com.auth.authenticationservice.dto.UserProfile;
import com.auth.authenticationservice.exception.CustomUnAuthorizedException;
import com.auth.authenticationservice.filter.JwtUtils;
import com.auth.authenticationservice.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/v1.0/auth")
@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class AuthenticationController
{

    @Value("${secret.key}")
    String secret;


    private final UserService userService;

    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    public String generateToken(String username, String password) throws ServletException
    {
        String jwtToken;

        if(username==null || password == null)
        {
            throw new ServletException("Please enter valid username and password");
        }

        boolean flag= userService.loginUser(username, password);
        String role=userService.getRoleByUserAndPass(username,password);
        log.info(role+"--from dbb-- inside token"+username);

        if(!flag)
        {
            throw new ServletException("Invalid credentials");

        }
        else
        {
            log.info(role+"--last---");

            jwtToken=Jwts.builder().setSubject(username).claim("role",role)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .signWith(SignatureAlgorithm.HS256,secret).compact();

        }

        log.info(role+"--last---");
        log.info(jwtToken+"------token");
        return jwtToken;
    }

    @Operation(summary = "Generate Token")
    @PostMapping("/login")
    public ResponseEntity<AuthAccessToken> performLogin(@RequestBody UserProfile userProfile)
    {
        String username = userProfile.getUsername();
        String password = userProfile.getPassword();
        AuthAccessToken authAccessToken = new AuthAccessToken();
        log.info(username+"----");
        boolean check=userService.loginUser(username,password);
        log.info(check+"");
        if(check){
            String role=userService.getRoleByUserAndPass(username, password);
            log.info(role);
            try
            {
                log.info(generateToken(username, password));
                String jwtToken = generateToken(username, password);
                log.info(password);
                log.info(jwtToken+" inside login");
                if(role.equalsIgnoreCase("admin"))
                {

                    authAccessToken.setMessage("Admin successfully logged in");
                    log.info(jwtToken);
                    authAccessToken.setJwtToken(jwtToken);
                    authAccessToken.setRole(role);
                    authAccessToken.setUsername(username);
                    return new ResponseEntity<>(authAccessToken, HttpStatus.CREATED);

                }
                else if(role.equalsIgnoreCase("User"))
                {
                    authAccessToken.setMessage("User successfully logged in");
                    authAccessToken.setJwtToken(jwtToken);
                    authAccessToken.setRole(role);
                    authAccessToken.setUsername(username);
                    return new ResponseEntity<>(authAccessToken, HttpStatus.CREATED);
                }

            }

            catch( ServletException e)
            {
                log.info(e+"exception");
                authAccessToken.setMessage("Failed to logged in");
                authAccessToken.setJwtToken(null);
                authAccessToken.setRole(null);
            }

        }
        throw new CustomUnAuthorizedException("UnAuthorized User");

    }

    @Operation(summary = "Validate User")
    @PostMapping("/validate")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> validateToken(@RequestHeader("Authorization") String token) {
        log.info(token+"---------");
        if (jwtUtils.validateJwtToken(token)) {
            log.info("-------validate-------");
            Map<String, String> userInfo = new HashMap<>();
            String authToken = token.substring(7);
            String username = jwtUtils.getUserNameFromJwtToken(authToken);
            String role = jwtUtils.getRoleFromToken(authToken);
            userInfo.put(username, role);
            log.info(userInfo.toString());
            return ResponseEntity.status(HttpStatus.OK).body(userInfo);
        } else {
            log.info("-------***********-------");
            throw new CustomUnAuthorizedException("Invalid Token");
        }
    }
}

