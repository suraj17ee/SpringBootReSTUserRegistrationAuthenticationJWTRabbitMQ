package com.stackroute.controller;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.entity.User;
import com.stackroute.entity.UserDto;
import com.stackroute.service.UserService;
@Slf4j
@RestController
@RequestMapping("authenticate")
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    // save user
    @RabbitListener(queues="queueA")
    public void registerNewUser(UserDto userDto) {
        User newUser = userService.registerNewUser(userDto);
        log.info("UserController : registerNewUser :"+ newUser.getUserEmail() +"registered successfully!!");
    }

    @GetMapping({"/shopkeeper/login"})
    @PreAuthorize("hasRole('SHOPKEEPER')")
    public ResponseEntity<String> forAdmin(){
        log.info("UserController : forAdmin : for admin method called!!");
        return new ResponseEntity<>("This URL is only accessible to the shopkeepers",HttpStatus.OK);
    }

    @GetMapping({"/farmer/login"})
    @PreAuthorize("hasRole('FARMER')")
    public ResponseEntity<String> forUser(){
        log.info("UserController : forUser : for user method called!!");
        return new ResponseEntity<>("This URL is only accessible to the farmers",HttpStatus.OK);
    }
}

