package com.cts.moviebooking;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class MyPostFilter implements GlobalFilter, Ordered {

    final Logger logger = LoggerFactory.getLogger(MyPostFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return chain.filter(exchange).then(Mono.fromRunnable(()-> {

            logger.info("My last post filter is executed");
            ServerHttpResponse response = exchange.getResponse();

            // Log the response status code and other details as needed
            int statusCode = response.getRawStatusCode();
            String responseBody = "Response body here"; // You can extract the response body if needed
            // Log or process the response as needed

            logger.info("Response Status Code: " + statusCode);

            System.out.println("Response Body: " + responseBody);

        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
