package com.stackroute.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.entity.JwtRequest;
import com.stackroute.entity.JwtResponse;
import com.stackroute.service.JwtService;
@Slf4j
@RestController
@RequestMapping("authenticate")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/generateToken"})
    public ResponseEntity<JwtResponse> createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
    	JwtResponse createJwtToken = jwtService.createJwtToken(jwtRequest);
        log.info("JwtController : createJwtToken : create jwt token method working!!");
    	return new  ResponseEntity<JwtResponse>(createJwtToken,HttpStatus.OK);
    }
}