package com.BlogApp.ServicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApp.Entities.User;
import com.BlogApp.Exception.ResourceNotFoundException;
import com.BlogApp.Payloads.UserDto;
import com.BlogApp.Repository.UserRepository;
import com.BlogApp.Services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		User userInfo = modelMapper.map(userDto, User.class);
		User saveUser = userRepository.save(userInfo);
		return modelMapper.map(saveUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updateUser = userRepository.save(user);
		return modelMapper.map(updateUser, UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = users.stream().map((user) -> modelMapper.map(users, UserDto.class)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		userRepository.delete(user);
	}
}
