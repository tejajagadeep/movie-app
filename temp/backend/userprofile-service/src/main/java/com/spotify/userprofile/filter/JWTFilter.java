package com.spotify.userprofile.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Configuration
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    @Value("${secret.key}")
    String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {


        final String requestTokenHeader = request.getHeader("Authorization");
        log.info(requestTokenHeader+"-----------------------+++");

        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

            //yes
            jwtToken = requestTokenHeader.substring(7);
        log.info(jwtToken);

            try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody();

            request.setAttribute("username", claims);
         log.info( request.getAttribute("username")+"from claimss------======");
        }catch (ExpiredJwtException e){

            throw new ExpiredJwtException(e.getHeader(),e.getClaims(),"JWT Expired");
        }


        } else {
            log.info("Invalid token , not start with bearer string");
        }

        filterChain.doFilter(request, response);


    }

}
