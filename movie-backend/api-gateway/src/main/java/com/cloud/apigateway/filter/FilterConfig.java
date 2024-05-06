package com.cloud.apigateway.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtFilterChain> jwtFilter() {
        FilterRegistrationBean<JwtFilterChain> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new JwtFilterChain());
        filterRegistrationBean.addUrlPatterns("/api/v1.0/private/*"); // URL patterns to apply the filter
        return filterRegistrationBean;
    }
}
