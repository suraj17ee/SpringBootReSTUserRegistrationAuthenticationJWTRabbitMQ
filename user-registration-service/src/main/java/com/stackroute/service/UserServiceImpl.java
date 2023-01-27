package com.stackroute.service;

import static com.stackroute.entity.User.SEQUENCE_NAME;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stackroute.entity.User;
import com.stackroute.entity.UserDto;
import com.stackroute.exception.PersonNotFoundException;
import com.stackroute.exception.UserFileNotFoundException;
import com.stackroute.repository.UserRepository;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

	private UserRepository repo;
	private SequenceGeneratorService sequence;

	@Autowired
	public UserServiceImpl(UserRepository repo, SequenceGeneratorService service) {
		this.repo = repo;
		this.sequence = service;
	}

	@Override
	public Integer saveUser(UserDto userDto) {
		if (userDto == null)
		{
			log.error("UserServiceImpl : saveUser : exception caught at user service save user method!!");
			throw new PersonNotFoundException("Please provide all the user details!!");
		}
		else if(repo.findUserByUserEmail(userDto.getUserEmail())!=null){
			throw new UserFileNotFoundException("user has already registered!!");
		}
		else if(userDto.getUserRole().equals("FARMER") || userDto.getUserRole().equals("SHOPKEEPER"))
		{
			User user = new User();
			user.setUserRegisterId(sequence.getSequenceNumber(SEQUENCE_NAME));
			user.setUserEmail(userDto.getUserEmail());
			user.setUserFirstName(userDto.getUserFirstName());
			user.setUserLastName(userDto.getUserLastName());
			user.setUserMobileNumber(userDto.getUserMobileNumber());
			user.setUserDob(userDto.getUserDob());
			user.setUserGender(userDto.getUserGender());
			user.setUserAddress(userDto.getUserAddress());
			user.setUserRole(userDto.getUserRole());
			log.info("UserServiceImpl : saveUser : user repository save method called!!");
			return repo.save(user).getUserRegisterId();

		}
		else
		{
			log.error("UserServiceImpl : saveUser : exception caught at user service save user method!!");
			throw new UserFileNotFoundException("you can only register as FARMER or SHOPKEEPER!!");
		}
	}

	@Override
	public List<User> getAllUsers() {
		return repo.findAll();
	}

	@Override
	public User getUserById(Integer uid) {
		Optional<User> optionalUser = repo.findById(uid);
		if (optionalUser.isEmpty()) {
			log.error("UserServiceImpl : getUserById : exception caught at user service get user by id method!!");
			throw new PersonNotFoundException("Person not exists with id: " + uid + " !!");
		} else {
			log.info("UserServiceImpl : getUserById : user repository find by id method called!!");
			return optionalUser.get();
		}
	}

	@Override
	public User findUserByEmail(String email) {
		if (email.isEmpty()) {
			log.error("UserServiceImpl : findUserByEmail : exception caught at user service find user by email method!!");
			throw new UserFileNotFoundException("Please provide email!!");
		} else {
			User user = repo.findUserByUserEmail(email);
			if (user == null) {
				log.error("UserServiceImpl : findUserByEmail : exception caught at user service find user by email method!!");
				throw new PersonNotFoundException("Person not exists!!");
			} else {
				log.info("UserServiceImpl : findUserByEmail : user repository find user by user email method called!!");
				return user;
			}
		}
	}

	@Override
	public void removeUserById(Integer uid) {
		User user = getUserById(uid);
		if (user == null) {
			log.error("UserServiceImpl : removeUserById : exception caught at user service remove user by id method!!");
			throw new PersonNotFoundException("Person not exists with id: " + uid + " to remove!!");
		} else {
			log.info("UserServiceImpl : removeUserById : user repository delete method called!!");
			repo.delete(user);
		}
	}

	@Override
	public void updateUser(Integer uid, UserDto userDto) {
		if (uid != null && repo.existsById(uid)) {
			User dbUser = getUserById(uid);
			dbUser.setUserEmail(userDto.getUserEmail());
			dbUser.setUserLastName(userDto.getUserLastName());
			dbUser.setUserMobileNumber(userDto.getUserMobileNumber());
			dbUser.setUserAddress(userDto.getUserAddress());
			log.info("UserServiceImpl : updateUser : user repository save method called!!");
			repo.save(dbUser);
		} else {
			log.error("UserServiceImpl : updateUser : exception caught at user service update user method!!");
			throw new PersonNotFoundException("Person not exists with id: " + uid + " to update!!");
		}
	}

	@Override
	public void updateUserMobile(Integer uid, UserDto userDto) {
		if (uid != null && repo.existsById(uid)) {
			User dbUser = getUserById(uid);
			dbUser.setUserMobileNumber(userDto.getUserMobileNumber());
			log.info("UserServiceImpl : updateUserMobile : user repository save method called!!");
			repo.save(dbUser);
		} else {
			log.error("UserServiceImpl : updateUserMobile : exception caught at user service update user mobile method!!");
			throw new PersonNotFoundException("Person not exists with id: " + uid + " to update!!");
		}
	}

	@Override
	public void updateUserPassword(Integer uid, UserDto userDto) {
		if (uid != null && repo.existsById(uid)) {
			User dbUser = getUserById(uid);
			dbUser.setUserPassword(userDto.getUserPassword());
			log.info("UserServiceImpl : updateUserPassword : user repository save method called!!");
			repo.save(dbUser);
		} else {
			log.error("UserServiceImpl : updateUserPassword : exception caught at user service update user password method!!");
			throw new PersonNotFoundException("Person not exists with id: " + uid + " to update!!");
		}
	}

	@Override
	public void updateUserAddress(Integer uid, UserDto userDto) {
		if (uid != null && repo.existsById(uid)) {
			User dbUser = getUserById(uid);
			dbUser.setUserAddress(userDto.getUserAddress());
			log.info("UserServiceImpl : updateUserAddress : user repository save method called!!");
			repo.save(dbUser);
		} else {
			log.error("UserServiceImpl : updateUserAddress : exception caught at user service update user address method!!");
			throw new PersonNotFoundException("Person not exists with id: " + uid + " to update!!");
		}
	}

	@Override
	public void updateProfilePictureByUserId(Integer uid, MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			log.error("UserServiceImpl : updateProfilePictureByUserId : exception caught at user service update profile picture by user id method!!");
			throw new UserFileNotFoundException("Please attach image!!");
		} else if (!file.getContentType().equals("image/jpeg")) {
			log.error("UserServiceImpl : updateProfilePictureByUserId : exception caught at user service update profile picture by user id method!!");
			throw new UserFileNotFoundException("Only JPEG format is allowed!!");
		} else if (!(file.getSize() < 5242880 && file.getSize() > 51200)) // image size in bytes between 5MB - 50KB
		{
			log.error("UserServiceImpl : updateProfilePictureByUserId : exception caught at user service update profile picture by user id method!!");
			throw new UserFileNotFoundException("Image size should be in between 50KB-5MB !!");
		} else if (repo.existsById(uid)) {
			User user = getUserById(uid);
			user.setImage(file.getBytes());// user profile picture
			log.info("UserServiceImpl : updateProfilePictureByUserId : user repository save method called!!");
			repo.save(user);
		} else {
			log.error("UserServiceImpl : updateProfilePictureByUserId : exception caught at user service update profile picture by user id method!!");
			throw new PersonNotFoundException("Person not exists with id: " + uid + " to update profile picture!!");
		}
	}

	@Override
	public byte[] getImageById(Integer uid) {
		if (repo.existsById(uid)) {
			User user = getUserById(uid);
			return user.getImage();
		} else {
			log.error("UserServiceImpl : getImageById : exception caught at user service get image by id method!!");
			throw new PersonNotFoundException("Person not exists with id: " + uid + " !!");
		}

	}

}