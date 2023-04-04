package com.lcwd.user.service.microservices.services;

import com.lcwd.user.service.microservices.entites.User;

import java.util.List;

public interface UserService {
    //create
    User saveUser(User user);

    //get all user
    List<User> getAllUser();

    //get single user of given userId
    User getUser(String userId);
}
