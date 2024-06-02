package com.puneet.blog.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {

	private int id;
	@Size(min = 4, message = "Username must be of min 4 Character")
	@NotEmpty(message = "Name can't be empty")
	private String name;
	@NotEmpty(message = "Email can't be empty")
	private String email;
	@Size(min = 4, max = 10, message = "password must be of min 4 Character & max 10 Character")
	@NotEmpty
	private String password;
	@NotBlank
	private String about;

	private Set<RoleDTO> roles = new HashSet<>();

	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password= password ;
	}

}
