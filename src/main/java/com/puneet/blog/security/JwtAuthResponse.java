package com.puneet.blog.security;

import com.puneet.blog.payload.UserDTO;

import lombok.Data;

@Data
public class JwtAuthResponse {
	
	private String token ;
	
	private UserDTO user ;

}
