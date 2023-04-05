package com.lcwd.user.service.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {



    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    //User (Class)
    //UserRepository(Interface)
    //UserService(Interface)
    //UserServiceImpl(Class)


}
