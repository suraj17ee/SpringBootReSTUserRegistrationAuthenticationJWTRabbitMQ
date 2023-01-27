package com.stackroute.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.stackroute.entity.User;

public interface UserRepository extends MongoRepository<User, Integer> {
	
//	User findTopByOrderByIdDesc();
	
	User findUserByUserEmail(String userEmail);

}
