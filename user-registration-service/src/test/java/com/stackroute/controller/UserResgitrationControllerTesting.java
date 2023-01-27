package com.stackroute.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.stackroute.entity.Address;
import com.stackroute.entity.User;
import com.stackroute.service.IUserService;

@AutoConfigureMockMvc
@SpringBootTest
public class UserResgitrationControllerTesting {

	@Autowired
    private MockMvc mockMvc;
	
	@InjectMocks
	UserController userController;
	
	@Mock
	IUserService userService;
	
	@Test
	public void getAllRegisteredUsersTest() throws Exception {
		when(userService.getAllUsers()).thenReturn(Stream
				.of(new User(1, "abc@gmail.com", "abc", "def", "7823589677", new Date(), "male", "abcdef@123","FARMER",
				List.of(new Address("karnataka", "blr", "blr", "760011")), null),
				new User(2, "xyz@gmail.com", "xyz", "uvw", "7822312567", new Date(), "female", "xyzuvw@123","SHOPKEEPER",
						List.of(new Address("maharashtra", "mum", "mum", "605411")), null))
		.collect(Collectors.toList()));
		mockMvc.perform(get("/user/all"))
        .andExpect(status().isFound()).andDo(print());
	}
	
	@Test
	public void getUserByIdTest() throws Exception {
		User user = new User(1, "abc@gmail.com", "abc", "def", "7823589677", new Date(), "male", "abcdef@123","FARMER",
				List.of(new Address("karnataka", "blr", "blr", "760011")), null);
		when(userService.getUserById(1)).thenReturn(user);
		mockMvc=MockMvcBuilders.standaloneSetup(userController).build();
        mockMvc.perform(get("/user/find/{uid}",user.getUserRegisterId()))
                .andExpect(status().isFound()).andDo(print());

	}
	@Test
	public void getUserByEmailTest() throws Exception {
		User user = new User(1, "abc@gmail.com", "abc", "def", "7823589677", new Date(), "male", "abcdef@123","FARMER",
				List.of(new Address("karnataka", "blr", "blr", "760011")), null);
		when(userService.findUserByEmail("abc@gmail.com")).thenReturn(user);
		mockMvc=MockMvcBuilders.standaloneSetup(userController).build();
        mockMvc.perform(get("/user/find/email/{userEmail}",user.getUserEmail()))
                .andExpect(status().isFound()).andDo(print());

	}
}
