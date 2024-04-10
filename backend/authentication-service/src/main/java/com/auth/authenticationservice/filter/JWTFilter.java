package com.auth.authenticationservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;



public class JWTFilter extends GenericFilterBean
{
	@Value("${secret.key}")
	String secret;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) request;

		String authHeader = httpReq.getHeader("authorization");

		if(authHeader ==null || !authHeader.startsWith("Bearer"))
		{
			throw new ServletException("Missing or invalid authentication header");
		}

		String jwtToken = authHeader.substring(6);

		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody();

		httpReq.setAttribute("username", claims);

		chain.doFilter(request, response);

	}


}




