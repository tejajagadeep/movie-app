package com.cloud.swaggerserver;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "SWAGGER-SERVER API", version = "1.0", description = "A centralized swagger server for movie microservices"))
public class SwaggerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwaggerServerApplication.class, args);
	}

}
