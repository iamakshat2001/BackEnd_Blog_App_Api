package com.blog.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.paylod.ApiResponse;
import com.blog.app.paylod.UserDto;
import com.blog.app.services.UserService;

import jakarta.validation.Valid;






@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService usersService;

	//POST-create User
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.usersService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	//POST all users
	@PostMapping("/all")
	public ResponseEntity<List<UserDto>> createMultipleUsers(@Valid @RequestBody List<UserDto> userDto){
		List<UserDto> createUserDto = this.usersService.InsertBulkData(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	// PUT - Update user

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto ,@PathVariable("userId") Integer uid){
		UserDto updateUserDto = this.usersService.updateUser(userDto,  uid);
		return ResponseEntity.ok(updateUserDto);
	}
	// DELETE - delete user

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		 this.usersService.deleteUser(uid);
		 return new ResponseEntity<>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
	}

	// GET - user get

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.usersService.getUserById(userId));
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getSingleUser(){
		return ResponseEntity.ok(this.usersService.getAllUsers());
	}
}
