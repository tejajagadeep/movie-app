package com.cts.wishlistservice.filter;

import java.io.Serializable;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
@Log4j2
public class JwtTokenUtil implements Serializable{

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5*60*60;

    @Value("${secret.key}")
    private String secret;

    public String getUsernameFromToken(String token) {
        log.info("token: {}", token);
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        log.info("token: {}", token);
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        log.info("token: {}", token);
        return claimsResolver.apply(claims);
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        log.info("token: {}", token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        log.info("token: {}", token);
        log.info("userDetails: {}", userDetails.toString());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public String getAuthoritiesString(String token) {
        return getAllClaimsFromToken(token).get("authorities").toString();
    }
    public Collection<? extends GrantedAuthority> getAuthorities(String token){
        List<String> authorityList = Arrays.asList(getAuthoritiesString(token).split(","));
        Collection<? extends GrantedAuthority> authorities =  authorityList.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        log.info("getAuthorities: {}", authorities.toString());
        return authorities;
    }
}