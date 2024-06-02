package com.puneet.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.puneet.blog.payload.UserDTO;


public interface UserService {
	
	UserDTO registerNewUser(UserDTO userDTO);

	UserDTO createUser(UserDTO user) ;
	
	UserDTO updateUser(UserDTO user, Integer userId ) ;
	
	UserDTO getUserById(Integer userId);
	
	List<UserDTO> getAllUsers();
	
	void deteleUser(Integer userId);
	
}  
