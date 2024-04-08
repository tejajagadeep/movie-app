package com.spotify.apigateway.filter;

import com.spotify.apigateway.exceptions.InvalidTokenException;
import com.spotify.apigateway.feign.AuthClient;
import com.spotify.apigateway.models.AuthResponse;
import com.spotify.apigateway.util.JwtUtil;
import com.spotify.apigateway.validator.RouteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    @Autowired
    private RouteValidator validator;
//    @Autowired
//    private AuthClient authClient;
    @Autowired
    private JwtUtil jwtUtil;
    public AuthFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if(validator.isSecured.test(exchange.getRequest())){
                System.out.println(exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION));
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Token is missing");
                }
                String authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                //System.out.println(authHeaders);
                if(authHeaders!=null && authHeaders.startsWith("Bearer ")){
                    authHeaders = authHeaders.substring(7);
                    //System.out.println(authHeaders);
                }
//                AuthResponse authResponse = authClient.validate(authHeaders);
//                if(!authResponse.isValid()){
//                    System.out.println("Invalid token");
//                    throw new RuntimeException("Unauthorized access!!");
//                }
                try{
                    System.out.println(authHeaders);
                    jwtUtil.validateToken(authHeaders);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Invalid access!");
                    throw new InvalidTokenException("Invalid Token!");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
