package com.cts.moviebooking.util;


import com.cts.moviebooking.dto.MovieDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(MovieDto.class, new MovieSerializer());
        registerModule(module);
    }
}
