package com.stackroute.service;

import com.stackroute.entity.User;
import com.stackroute.entity.UserDto;

public interface IUserService {
	User registerNewUser(UserDto userDto);
}
