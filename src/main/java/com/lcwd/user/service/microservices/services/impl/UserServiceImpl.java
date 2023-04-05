package com.lcwd.user.service.microservices.services.impl;

import com.lcwd.user.service.microservices.entites.Rating;
import com.lcwd.user.service.microservices.entites.User;
import com.lcwd.user.service.microservices.exception.ResourceNotFoundException;
import com.lcwd.user.service.microservices.repositories.UserRepository;
import com.lcwd.user.service.microservices.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    //databse meh save karne ke liye hume repository cahiye honga
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserID = UUID.randomUUID().toString();
        user.setUserId(randomUserID);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

   /* @Override
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server!! " + userId));
    }*/


    @Override
    public User getUser(String userId) {
        //get user from database with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server!! " + userId));

        //fetch rating of the above user  from RATING SERVICE
        //http://localhost:8083/ratings/users/b22b69cf-a109-4913-8823-6af4c3fac6fb
        //to call this api from rating service we use rest template
        //i.e., for calling api from another microservice we use rest template

        ArrayList<Rating> ratingsForUser = restTemplate.getForObject("http://localhost:8083/ratings/users/" + user.getUserId(), ArrayList.class);
        logger.info("{}", ratingsForUser);
        user.setRatings(ratingsForUser);
        return user;
    }
}
