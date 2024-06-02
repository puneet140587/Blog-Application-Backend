package com.puneet.blog.services;

import java.util.List;

import com.puneet.blog.payload.CategoryDTO;


public interface CategoryService {
	
	CategoryDTO createCategory (CategoryDTO categoryDTO);
	CategoryDTO updateCategory (CategoryDTO categoryDTO, Integer categoryId);
	void deleteCategory(Integer categoryId);
	CategoryDTO getCatgeory(Integer categoryId);
	List<CategoryDTO> getAllCategories();
}
