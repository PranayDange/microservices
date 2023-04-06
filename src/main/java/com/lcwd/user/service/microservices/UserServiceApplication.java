package com.lcwd.user.service.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class UserServiceApplication {



    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    //User (Class)
    //UserRepository(Interface)
    //UserService(Interface)
    //UserServiceImpl(Class)


    //feign client
    //the feign client is a declarative HTTP web client developed by netflix
    //if you want ot use feign ,create an interface and annotate it


}
