package com.cloud.apigateway.filter;

import com.cloud.apigateway.exception.UnAuthorizedException;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtFilterChain implements GlobalFilter {

    private final JwtService jwtService;

    public JwtFilterChain(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        // Skip JWT validation for public paths
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        String jwtToken = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            String token = jwtToken.substring(7); // Remove "Bearer " prefix
            boolean isValid = jwtService.validateToken(token);

            if (isValid) {
                return chain.filter(exchange);
            } else {
                throw new UnAuthorizedException("Invalid token");
            }
        } else {
            throw new UnAuthorizedException("Invalid token");
        }
    }

    private boolean isPublicPath(String path) {
        return path.matches("^/api/v1.0/public/.*");
    }
}
