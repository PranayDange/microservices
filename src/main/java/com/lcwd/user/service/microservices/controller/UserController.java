package com.lcwd.user.service.microservices.controller;

import com.lcwd.user.service.microservices.entites.User;
import com.lcwd.user.service.microservices.services.UserService;
import com.lcwd.user.service.microservices.services.impl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);


    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    int retryCount = 1;


    //get single user
    @GetMapping("/{userId}")
    // @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
   // @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    private ResponseEntity<User> getSingleUser(@PathVariable String userId) {
        // logger.info("get single user handler : usercontroller ");
        logger.info("retry count : {}", retryCount);
        retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //creating fallback method for circuit breaker
    //its return type must be same as its method in this case(getSingleUser)
    //fallback method will run when some service is down or service fails

    //this method is for circuit braker
   /* public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        logger.info("fallback is executed because service is down : ", ex.getMessage());
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("dummy")
                .about("This is dummy about to test")
                .userId("2342342")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);

    }*/
   /* //this method is for retry rejelliance 4j
    // int retryCount =1;
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        //   logger.info("fallback is executed because service is down : ", ex.getMessage());

        User user = User.builder()
                .email("dummy@gmail.com")
                .name("dummy")
                .about("This is dummy about to test")
                .userId("2342342")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);

    }*/


    //this method is for rate limiter rejelliance 4j
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        //   logger.info("fallback is executed because service is down : ", ex.getMessage());

        User user = User.builder()
                .email("dummy@gmail.com")
                .name("dummy")
                .about("This is dummy about to test")
                .userId("2342342")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    //get all
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}
