package com.blog.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourseNotFoundException;
import com.blog.app.paylod.UserDto;
import com.blog.app.repositories.UserRepo;
import com.blog.app.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userdto) {
		User user = this.dtoToUser(userdto);
        User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}



	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourseNotFoundException("User","Id",userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());

		User updateUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updateUser);

		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {

		User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourseNotFoundException("User","Id",userId));

			return this.userToDto(user);
	}


	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourseNotFoundException("User","Id",userId));
		this.userRepo.delete(user);

	}

	@Override
	public List<UserDto> InsertBulkData(List<UserDto> lUserDto) {


				List<User> users = lUserDto.stream()
				  .map(userDto -> this.dtoToUser(userDto))
						  .collect(Collectors.toList());
				  List<User> savedAllUsers = this.userRepo.saveAll(users);

			   List<UserDto> savedDtos = savedAllUsers.stream()
						  .map(savedAllUser -> new UserDto(savedAllUser.getId(),savedAllUser.getName(), savedAllUser.getEmail(),savedAllUser.getAbout(),savedAllUser.getPassword()))
								  .collect(Collectors.toList());

		return savedDtos;
	}

	//method for converting dto into user
	private User dtoToUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);

//		User u = new User();
//		u.setId(user.getId());
//		u.setName(user.getName());
//		u.setEamil(user.getEmail());
//		u.setAbout(user.getAbout());
//		u.setPassword(user.getPassword());
//
		return user;
	}

	// method for converting user into dto
	private UserDto userToDto(User us) {

		UserDto userDto = this.modelMapper.map(us, UserDto.class);

//		UserDto userdto = new UserDto();
//		userdto.setId(us.getId());
//		userdto.setName(us.getName());
//		userdto.setEmail(us.getEamil());
//		userdto.setAbout(us.getAbout());
//		userdto.setPassword(us.getPassword());

		return userDto;
	}





}
