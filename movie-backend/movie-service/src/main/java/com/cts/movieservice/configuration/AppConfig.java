package com.cts.movieservice.configuration;

import com.cts.movieservice.aspects.PerformanceTrackerHandler;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry){
        observationRegistry.observationConfig().observationHandler(new PerformanceTrackerHandler());
        return new ObservedAspect(observationRegistry);
    }

}
