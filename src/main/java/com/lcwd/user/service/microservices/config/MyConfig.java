package com.lcwd.user.service.microservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {

    //how to make a bean
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
