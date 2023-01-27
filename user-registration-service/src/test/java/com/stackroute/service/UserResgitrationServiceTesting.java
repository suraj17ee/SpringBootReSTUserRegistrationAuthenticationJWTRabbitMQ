package com.stackroute.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.stackroute.entity.Address;
import com.stackroute.entity.User;
import com.stackroute.repository.UserRepository;

@SpringBootTest
public class UserResgitrationServiceTesting {

	@Autowired
	private IUserService service;

	@MockBean
	private UserRepository repo;

	@Test
	void getAllUsersTest() {
		when(repo.findAll()).thenReturn(Stream
				.of(new User(1, "abc@gmail.com", "abc", "def", "7823589677", new Date(), "male", "abcdef@123","FARMER",
						List.of(new Address("karnataka", "blr", "blr", "760011")), null),
						new User(2, "xyz@gmail.com", "xyz", "uvw", "7822312567", new Date(), "female", "xyzuvw@123","SHOPKEEPER",
								List.of(new Address("maharashtra", "mum", "mum", "605411")), null))
				.collect(Collectors.toList()));
		assertEquals(2, service.getAllUsers().size());
	}

	@Test
	void getUserByIdTest() {
		Integer uid = 1;
		when(repo.findById(uid)).thenReturn(Optional.of(new User(1, "abc@gmail.com", "abc", "def", "7823589677",
				new Date(), "male", "abcdef@123","FARMER", List.of(new Address("karnataka", "blr", "blr", "760011")), null)));
		assertEquals(1, service.getUserById(uid).getUserRegisterId());
	}

    @Test
    void removeByIdTest() throws IOException {
    	when(repo.findById(1)).thenReturn(Optional.of(new User(1, "abc@gmail.com", "abc", "def", "7823589677",
				new Date(), "male", "abcdef@123","FARMER", List.of(new Address("karnataka", "blr", "blr", "760011")), null)));
    	User user = repo.findById(1).get();
    	service.removeUserById(user.getUserRegisterId());
		verify(repo, times(1)).delete(user);
    }

}
