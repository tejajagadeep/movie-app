package com.cts.moviebooking.config;

import com.cts.moviebooking.dto.MovieDto;
import com.cts.moviebooking.util.CustomObjectMapper;
import com.cts.moviebooking.util.PageDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
public class JacksonConfig {

    @Bean
    public CustomObjectMapper customObjectMapper() {
        return new CustomObjectMapper();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeJackson() {
        return jacksonObjectMapperBuilder -> {
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Page.class, new PageDeserializer<>(MovieDto.class));
            jacksonObjectMapperBuilder.modules(module);
        };
    }

}
