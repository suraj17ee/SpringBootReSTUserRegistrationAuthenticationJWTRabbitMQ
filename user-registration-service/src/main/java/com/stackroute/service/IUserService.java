package com.stackroute.service;

import com.stackroute.entity.User;
import com.stackroute.entity.UserDto;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface IUserService {

     Integer saveUser(UserDto userDto);

     List<User> getAllUsers();

     User getUserById(Integer uid);

     void removeUserById(Integer uid);

     void updateUser(Integer uid, UserDto userDto);

     void updateUserMobile(Integer uid, UserDto userDto);

     void updateUserPassword(Integer uid, UserDto userDto);

     void updateUserAddress(Integer uid, UserDto userDto);

     void updateProfilePictureByUserId(Integer uid,MultipartFile file) throws IOException;

     User findUserByEmail(String email);

     byte[] getImageById(Integer uid);
}