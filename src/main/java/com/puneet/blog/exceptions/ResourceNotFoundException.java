package com.puneet.blog.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ResourceNotFoundException extends RuntimeException {

	String resourceName;
	String fieldName;
	Integer fieldValue;

	public ResourceNotFoundException(String resourceName, String fieldName, Integer userId) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, userId));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = userId;
	}

}
