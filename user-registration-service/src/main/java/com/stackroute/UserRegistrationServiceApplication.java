package com.stackroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient

public class UserRegistrationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserRegistrationServiceApplication.class, args);
	}

}
