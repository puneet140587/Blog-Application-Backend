package com.puneet.blog.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class CategoryDTO {
	
	private Integer categoryId;
	@NotBlank
	@Size(min = 4,message = "Min size of category title is 4")
	private String categoryTitle;

	@NotBlank
	@Size(min = 10, message = "min size of cateogry desc is 10")
	private String categoryDescription;

}
