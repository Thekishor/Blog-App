package com.BlogApp.Services;

import java.util.List;

import com.BlogApp.Payloads.UserDto;

public interface UserService {
	
	public UserDto createUser(UserDto userDto);
	public UserDto updateUser(UserDto userDto, Integer id);
	public UserDto getUserById(Integer id);
	public List<UserDto> getAllUsers();
	public void deleteUser(Integer id);
}
