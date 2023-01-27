package com.stackroute.controller;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;

import com.stackroute.entity.UserMq;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.stackroute.entity.User;
import com.stackroute.entity.UserDto;
import com.stackroute.exception.PersonNotFoundException;
import com.stackroute.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

	private IUserService userService;

	private RabbitTemplate rabbitTemplate;

	@Autowired
	public UserController(IUserService userService,RabbitTemplate rabbitTemplate) {
		this.userService = userService;
		this.rabbitTemplate = rabbitTemplate;
	}

	// save user registration data
	@PostMapping(value = "/register")
	public ResponseEntity<String> registerUser(@RequestBody @Valid UserDto userDto) {
		Integer uid = userService.saveUser(userDto);
		log.info("UserController : registerUser : user service's save user method called!!");
		UserMq usermq = new UserMq();
		usermq.setUserEmail(userDto.getUserEmail());
		usermq.setUserPassword(userDto.getUserPassword());
		usermq.setUserRole(userDto.getUserRole());
		rabbitTemplate.convertAndSend("MyExchange","routingA",usermq);
		log.info("UserController : registerUser : user credentials sent to rabbit mq!!");
		return new ResponseEntity<>("User with id: " + uid + " has registered successfully!!", HttpStatus.CREATED);
	}

	// fetch all registered users data
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllRegisteredUsers() {
		List<User> allUsers = userService.getAllUsers();
		log.info("UserController : getAllRegisteredUsers : user service's get all users method called!!");
		return new ResponseEntity<>(allUsers, HttpStatus.FOUND);
	}

	// fetch user by id
	@GetMapping("/find/{uid}")
	public ResponseEntity<User> getUserById(@PathVariable Integer uid) {
		try {
			User user = userService.getUserById(uid);
			log.info("UserController : getUserById : user service's get user by id method called!!");
			return new ResponseEntity<>(user, HttpStatus.FOUND);
		} catch (PersonNotFoundException e) {
			log.error("UserController : getUserById : exception caught while user service's get user by id method called!!");
			e.printStackTrace();
			throw e;
		}
	}

	// fetch user by email
	@GetMapping("/find/email/{userEmail}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String userEmail) {
		try {
			User user = userService.findUserByEmail(userEmail);
			log.info("UserController : getUserByEmail : user service's find user by email method called!!");
			return new ResponseEntity<>(user, HttpStatus.FOUND);
		} catch (PersonNotFoundException e) {
			log.error("UserController : getUserByEmail : exception caught while user service's get user by email method called!!");
			e.printStackTrace();
			throw e;
		}
	}


	@PatchMapping("/update/password/{uid}")
	public ResponseEntity<String> updateUserPasswordByUserId(@RequestBody UserDto userDto, @PathVariable Integer uid) {
		ResponseEntity<String> response = null;
		try {
			userService.updateUserPassword(uid,userDto);
			log.info("UserController : updateUserPasswordByUserId: user service's update user password method called!!");
			response = new ResponseEntity<>("User password has been updated successfully!!", HttpStatus.OK);
		} catch (PersonNotFoundException e) {
			e.printStackTrace();
			log.error("UserController : updateUserPasswordByUserId: exception caught while user service's update user password method called!!");
			throw e;
		}
		return response;
	}

	// delete user by id
	@DeleteMapping("/remove/{uid}")
	public ResponseEntity<String> removeUserById(@PathVariable Integer uid) {
		try {
			userService.removeUserById(uid);
			log.info("UserController : removeUserById : user service's remove user by id method called!!");
			return new ResponseEntity<>("User has removed successfully!!", HttpStatus.OK);
		} catch (PersonNotFoundException e) {
			log.error("UserController : removeUserById : exception caught while user service's remove user by id method called!!");
			e.printStackTrace();
			throw e;
		}
	}


	// update user by id
	@PutMapping("/update/{uid}")
	public ResponseEntity<String> updateUserData(@PathVariable Integer uid,@RequestBody @Valid UserDto userDto){
		try {
			userService.updateUser(uid,userDto);
			log.info("UserController : updateUserData : user service's update user method called!!");
			return new ResponseEntity<>("User has updated successfully!!", HttpStatus.OK);
		} catch (PersonNotFoundException e) {
			log.error("UserController : updateUserData : exception caught while user service's user update method called!!");
			e.printStackTrace();
			throw e;
		}
	}

	// update user mobile
	@PatchMapping("/update/mobile/{uid}")
	public ResponseEntity<String> updateUserMobile(@PathVariable Integer uid,@RequestBody @Valid UserDto userDto){
		try {
			userService.updateUserMobile(uid,userDto);
			log.info("UserController : updateUserMobile : user service's update user mobile method called!!");
			return new ResponseEntity<>("User has updated successfully!!", HttpStatus.OK);
		} catch (PersonNotFoundException e) {
			log.error("UserController : updateUserMobile : exception caught while user service's user update method called!!");
			e.printStackTrace();
			throw e;
		}
	}

	// update user password
	@PatchMapping("/update/pass/{uid}")
	public ResponseEntity<String> updateUserPassword(@PathVariable Integer uid,@RequestBody @Valid UserDto userDto){
		try {
			userService.updateUserPassword(uid,userDto);
			log.info("UserController : updateUserPassword : user service's update user password method called!!");
			return new ResponseEntity<>("User has updated successfully!!", HttpStatus.OK);
		} catch (PersonNotFoundException e) {
			log.error("UserController : updateUserPassword : exception caught while user service's update user password method called!!");
			e.printStackTrace();
			throw e;
		}
	}

	// update user address
	@PatchMapping("/update/address/{uid}")
	public ResponseEntity<String> updateUserAddress(@PathVariable Integer uid,@RequestBody @Valid UserDto userDto){
		try {
			userService.updateUserAddress(uid,userDto);
			log.info("UserController : updateUserAddress : user service's update user address method called!!");
			return new ResponseEntity<>("User has updated successfully!!", HttpStatus.OK);
		} catch (PersonNotFoundException e) {
			log.error("UserController : updateUserAddress : exception caught while user service's update user address method called!!");
			e.printStackTrace();
			throw e;
		}
	}

	// update user profile picture by user id
	@PatchMapping(value = "/update/image/{uid}",consumes = {"multipart/form-data"})
	@Operation(summary = "uploadImage")
	public ResponseEntity<String> updateUserProfilePictureByUserId(@RequestParam("image") MultipartFile file,
																   @PathVariable Integer uid) throws IOException {
		try {
			userService.updateProfilePictureByUserId(uid,file);
			log.info("UserController : updateUserProfilePictureByUserId : user service's update profile picture by user id method called!!");
			return new ResponseEntity<>("User profile picture has been updated successfully!!", HttpStatus.OK);
		} catch (PersonNotFoundException e) {
			log.error("UserController : updateUserProfilePictureByUserId : exception caught while user service's update profile picture by user id method called!!");
			e.printStackTrace();
			throw e;
		}
	}

	//get image by id
	@GetMapping("/image/{uid}")
	public ResponseEntity<byte[]> downloadImage(@PathVariable Integer uid){
		byte[] imageData=userService.getImageById(uid);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/jpeg"))
				.body(imageData);
	}

}