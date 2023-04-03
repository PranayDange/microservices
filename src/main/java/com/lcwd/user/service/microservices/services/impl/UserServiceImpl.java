package com.lcwd.user.service.microservices.services.impl;

import com.lcwd.user.service.microservices.entities.User;
import com.lcwd.user.service.microservices.exception.ResourceNotFoundException;
import com.lcwd.user.service.microservices.repositories.UserRepository;
import com.lcwd.user.service.microservices.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    //databse meh save karne ke liye hume repository cahiye honga
    @Autowired
    private UserRepository userRepository;

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

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server!! " + userId));
    }
}
