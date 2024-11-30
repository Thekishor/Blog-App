package com.BlogApp.Controllers;

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

import com.BlogApp.Payloads.ApiResponse;
import com.BlogApp.Payloads.UserDto;
import com.BlogApp.Services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto userDtoInfo = userService.createUser(userDto);
		return new ResponseEntity<UserDto>(userDtoInfo, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer id) {
		UserDto userDtoInfo = userService.updateUser(userDto, id);
		return new ResponseEntity<UserDto>(userDtoInfo, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(new ApiResponse("User Deleted successfully", true), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
		UserDto userDto = userService.getUserById(id);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> userDtos = userService.getAllUsers();
		return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
	}
}
