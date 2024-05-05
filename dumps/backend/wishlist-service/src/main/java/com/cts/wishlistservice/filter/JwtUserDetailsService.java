package com.cts.wishlistservice.filter;

import java.util.*;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@Getter
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        log.info(("username: "+username));
        return new User(username, "12345", jwtTokenUtil.getAuthorities(token)
                    );
    }
}
