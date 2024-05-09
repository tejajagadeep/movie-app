package com.user.userprofileservice.configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${swagger.server.url}")
    private String swaggerUrl;

    @Value("${allowed.cors.urls}")
    private String allowedCors;

    private String concatCors(){
        StringBuilder builder = new StringBuilder();
        builder.append(swaggerUrl);
        builder.append(",");
        builder.append(allowedCors);
        return builder.toString();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(concatCors().split(","))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}

