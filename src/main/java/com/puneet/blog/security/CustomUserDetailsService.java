package com.puneet.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.puneet.blog.entites.User;
import com.puneet.blog.exceptions.ResourceNotFoundException;
import com.puneet.blog.repository.UserRepository;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository ;

	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Loading user from  Database by username

		User user = userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","email :"+ username, 0));
		
		return user;
	}

}
