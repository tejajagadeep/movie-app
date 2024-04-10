package com.auth.authenticationservice.filter;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtils {

    @Value("${secret.key}")
    String secret;

    

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Claims getJwtClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // will need this later
    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();


        return (String) claims.get("role");
    }

    public boolean validateJwtToken(String token) {// fix me
        String authToken = null;
        String user = null;
        if (token != null && token.startsWith("Bearer ")) {
            authToken = token.substring(7);
            try {
                user = getUserNameFromJwtToken(authToken);
                log.info(user);// fix
                return true;
            } catch (SignatureException e) {
                log.error("Invalid JWT signature: {} " + e.getMessage());
                // later --fix me
            } catch (MalformedJwtException e) {
                log.error("Invalid JWT signature: {} " + e.getMessage());
            } catch (ExpiredJwtException e) {
                log.error("JWT token is expired: {} " + e.getMessage());
            } catch (UnsupportedJwtException e) {
                log.error("JWT token is unsupported: {} " + e.getMessage());
            } catch (IllegalArgumentException e) {
                log.error("JWT claims string is empty: {} " + e.getMessage());
            }
        }

        return false;
    }

}