package com.stackroute.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private String userEmail;
	private String userPassword;
	private String userRole;

}
