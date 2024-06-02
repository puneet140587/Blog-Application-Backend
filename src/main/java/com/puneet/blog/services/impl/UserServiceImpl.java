package com.puneet.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.puneet.blog.config.AppConstant;
import com.puneet.blog.entites.Role;
import com.puneet.blog.entites.User;
import com.puneet.blog.exceptions.ResourceNotFoundException;
import com.puneet.blog.payload.UserDTO;
import com.puneet.blog.repository.RoleRepository;
import com.puneet.blog.repository.UserRepository;
import com.puneet.blog.services.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired 
	private UserRepository userRepository ;
	
	@Autowired
	private ModelMapper modelMapper ;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository ;

	@Override
	public UserDTO createUser(UserDTO userDto) {
		User user = dtoToUser(userDto);
		User savedUser = userRepository.save(user);
		return userToDto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = userRepository.save(user);
		UserDTO userDtoUpdated = userToDto(updatedUser);
		return userDtoUpdated;
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id" , userId));
		return userToDto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDTO> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		return userDtos ;
	}

	@Override
	public void deteleUser(Integer userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id" , userId));
		userRepository.delete(user);
	}
	
	private User dtoToUser(UserDTO userDTO) {
		User user = modelMapper.map(userDTO, User.class);
//		user.setId(userDTO.getId());
//		user.setName(userDTO.getName()); 
//		user.setEmail(userDTO.getEmail());
//		user.setPassword(userDTO.getPassword());
//		user.setAbout(userDTO.getAbout());
		return user ; 
	}
	
	private UserDTO userToDto(User user) {
		UserDTO userDto = modelMapper.map(user, UserDTO.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto ; 
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDTO) {
		User user = modelMapper.map(userDTO, User.class);
		
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Role role = roleRepository.findById(AppConstant.ROLE_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = userRepository.save(user);
		
		return modelMapper.map(newUser, UserDTO.class);
	}
	

}
