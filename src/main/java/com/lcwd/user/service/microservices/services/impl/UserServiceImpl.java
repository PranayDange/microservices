package com.lcwd.user.service.microservices.services.impl;

import com.lcwd.user.service.microservices.entites.Hotel;
import com.lcwd.user.service.microservices.entites.Rating;
import com.lcwd.user.service.microservices.entites.User;
import com.lcwd.user.service.microservices.exception.ResourceNotFoundException;
import com.lcwd.user.service.microservices.external.services.HotelService;
import com.lcwd.user.service.microservices.repositories.UserRepository;
import com.lcwd.user.service.microservices.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    //databse meh save karne ke liye hume repository cahiye honga
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

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


    //using rest template

  /*  @Override
    public User getUser(String userId) {
        //get user from database with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server!! " + userId));

        //fetch rating of the above user  from RATING SERVICE
        //http://localhost:8083/ratings/users/b22b69cf-a109-4913-8823-6af4c3fac6fb
        //to call this api from rating service we use rest template or feign client
        //i.e., for calling api from another microservice we use rest template

        Rating[] ratingsForUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        logger.info("{}", ratingsForUser);

        List<Rating> ratings = Arrays.stream(ratingsForUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel

            //http://localhost:8082/hotels/79260eaf-00e1-42dc-89ad-7be48f2e6021
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();
            logger.info("response status code : {}", forEntity.getStatusCode());
            //set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());


        user.setRatings(ratingList);
        return user;
    }*/

    //using feign client

    @Override
    public User getUser(String userId) {
        //get user from database with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server!! " + userId));

        //fetch rating of the above user  from RATING SERVICE
        //http://localhost:8083/ratings/users/b22b69cf-a109-4913-8823-6af4c3fac6fb
        //to call this api from rating service we use rest template or feign client
        //i.e., for calling api from another microservice we use rest template

        Rating[] ratingsForUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        logger.info("{}", ratingsForUser);

        List<Rating> ratings = Arrays.stream(ratingsForUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel

            //http://localhost:8082/hotels/79260eaf-00e1-42dc-89ad-7be48f2e6021
            //  ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            // logger.info("response status code : {}", forEntity.getStatusCode());
            //set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());


        user.setRatings(ratingList);
        return user;
    }
}
