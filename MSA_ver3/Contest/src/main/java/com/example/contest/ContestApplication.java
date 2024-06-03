package com.example.contest;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class ContestApplication {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
