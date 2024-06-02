package com.puneet.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puneet.blog.entites.Category;
import com.puneet.blog.exceptions.ResourceNotFoundException;
import com.puneet.blog.payload.CategoryDTO;
import com.puneet.blog.repository.CategoryRepository;
import com.puneet.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper ;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category newCat = modelMapper.map(categoryDTO, Category.class);
		Category addedCategory = categoryRepository.save(newCat);
		return modelMapper.map(addedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
		Category oldCat = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		oldCat.setCategoryTitle(categoryDTO.getCategoryTitle());
		oldCat.setCategoryDescription(categoryDTO.getCategoryDescription());
		Category updatedCategory = categoryRepository.save(oldCat);
		return modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category oldCat = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		categoryRepository.delete(oldCat); 
	}

	@Override
	public CategoryDTO getCatgeory(Integer categoryId) {
		Category oldCat = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		return modelMapper.map(oldCat, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDTO> categoryDTO = categories.stream().map((cat) -> modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
		return categoryDTO;
	}

}
