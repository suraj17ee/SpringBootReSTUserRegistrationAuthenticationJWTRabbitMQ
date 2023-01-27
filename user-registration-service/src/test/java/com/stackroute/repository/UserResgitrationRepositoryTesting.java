package com.stackroute.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.stackroute.entity.Address;
import com.stackroute.entity.User;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UserResgitrationRepositoryTesting {

	@Autowired
	private UserRepository repo;

	@Order(1)
	@Test
	void saveUserTest() {
		List<Address> address = new ArrayList<>();
		address.add(new Address("maharashtra", "mum", "mum", "605411"));
		User user = User.builder().userRegisterId(1).userEmail("abc@gmail.com").userFirstName("abc").userLastName("def")
				.userMobileNumber("8978675689").userDob(new Date()).userGender("male").userPassword("abc123").userRole("FARMER")
				.image(null).userAddress(address).build();
		repo.save(user);
		assertThat(user.getUserRegisterId()).isGreaterThan(0);
	}

	@Order(2)
	@Test
	void getUserByEmailTest() {
		User user = repo.findUserByUserEmail("abc@gmail.com");
		assertThat(user.getUserEmail()).isEqualTo("abc@gmail.com");
	}

	@Order(3)
	@Test
	void getUserByIdTest() {
		User user = repo.findById(1).get();
		assertThat(user.getUserRegisterId()).isEqualTo(1);
	}
	
	@Order(4)
	@Test
	void getAllUsersTest() {
		List<User> users = repo.findAll();
		assertThat(users.size()).isGreaterThan(0);
	}

	@Order(5)
	@Test
	void updateUserMobileTest() {
		User user = repo.findById(1).get();
		user.setUserEmail("abcd@gmail.com");
		User updatedUser = repo.save(user);
		assertThat(updatedUser.getUserEmail()).isEqualTo("abcd@gmail.com");
	}

	@Order(6)
	@Test
	void removeUserByIdTest() {
		User user = repo.findById(1).get();
		repo.delete(user);
		User dbUser = repo.findUserByUserEmail("abcd@gmail.com");
		assertThat(dbUser).isNull();
	}
}
