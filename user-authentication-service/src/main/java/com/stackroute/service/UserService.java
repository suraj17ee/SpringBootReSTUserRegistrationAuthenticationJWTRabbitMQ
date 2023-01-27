package com.stackroute.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stackroute.entity.Role;
import com.stackroute.entity.User;
import com.stackroute.entity.UserDto;
import com.stackroute.repository.IRoleRepository;
import com.stackroute.repository.IUserRepository;

@Service
public class UserService implements IUserService{

	@Autowired
	private IUserRepository userRepo;

	@Autowired
	private IRoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	public void initRoleAndUser() {

		Role adminRole = new Role();
		adminRole.setRoleName("SHOPKEEPER");
		roleRepo.save(adminRole);

		Role userRole = new Role();
		userRole.setRoleName("FARMER");
		roleRepo.save(userRole);		
	}

	public User registerNewUser(UserDto userDto) {
		Role userRole = roleRepo.findById(userDto.getUserRole()).get();
		System.out.println(userRole);
		Set<Role> roles = new HashSet<>();
		roles.add(userRole);
		System.out.println(roles);
		
    	User newUser = new User();
    	newUser.setUserEmail(userDto.getUserEmail());
    	newUser.setUserPassword(getEncodedPassword(userDto.getUserPassword()));
    	newUser.setUserRole(roles);
		return userRepo.save(newUser);
	}


}
