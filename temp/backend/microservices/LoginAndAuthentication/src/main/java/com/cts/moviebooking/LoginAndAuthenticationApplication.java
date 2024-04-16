package com.cts.moviebooking;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class LoginAndAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginAndAuthenticationApplication.class, args);
	}


	@Bean
	public Logger.Level feignLogHandler()
	{
		return Logger.Level.FULL;
	}

}
