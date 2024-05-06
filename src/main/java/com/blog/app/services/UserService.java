package com.blog.app.services;

import java.util.List;

import com.blog.app.paylod.UserDto;

public interface UserService {

	UserDto createUser(UserDto user);
	List<UserDto> InsertBulkData(List<UserDto> lUserDto);
	UserDto updateUser(UserDto user , Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
    void deleteUser(Integer userId);



}
