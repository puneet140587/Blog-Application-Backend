package com.puneet.blog.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puneet.blog.entites.User;
import com.puneet.blog.exceptions.ApiException;
import com.puneet.blog.payload.UserDTO;
import com.puneet.blog.security.JwtAuthRequest;
import com.puneet.blog.security.JwtAuthResponse;
import com.puneet.blog.security.JwtTokenHelper;
import com.puneet.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper ;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserDetailsService userDetailsService ;
	
	@Autowired
	private AuthenticationManager authenticationManager ;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		
		authenticate(request.getUsername(),request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response = new JwtAuthResponse() ;
		response.setToken(token) ;
		response.setUser(modelMapper.map((User)userDetails, UserDTO.class)) ;
		
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK); 
		
	}


	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details !!");
			throw new ApiException("Invalid username or Password !!");
		}
	}
	
	// register new user api

		@PostMapping("/register")
		public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
			UserDTO registeredUser = this.userService.registerNewUser(userDTO);
			return new ResponseEntity<UserDTO>(registeredUser, HttpStatus.CREATED);
		}

}
