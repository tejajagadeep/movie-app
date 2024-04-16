package com.cts.moviebooking.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class AuthenticationService {

    @Value("${token.secret}")
    private String secretKey;


    @Value("${token.expiration_time}")
    private String expirationTime;



    public String generateToken(String userName, String userEmail,String role) {

        System.out.println("My secret key "+secretKey);
        System.out.println("user email "+userEmail);
        System.out.println(role+" in auth service My Role");

        //String tokenSecret = secretKey;
        byte[] secretKeyBytes = Base64.getEncoder().encode(secretKey.getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        Instant now = Instant.now();

        String token = Jwts.builder()
                .setSubject(userName)
                .claim("email", userEmail)
                .claim("role", role)
                .setExpiration(
                        Date.from(now.plusMillis(Long.parseLong(expirationTime))))
                .setIssuedAt(Date.from(now)).signWith(secretKey, SignatureAlgorithm.HS512).compact();
        return token;
    }
}
