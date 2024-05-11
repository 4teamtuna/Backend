package com.example.gatewayserver.GlobalFilter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalAuthFilterConfig {

    @Bean
    public GlobalFilter customFilter() {
        return new CustomGlobalFilter();
    }
}