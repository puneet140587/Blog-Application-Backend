package com.puneet.blog.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.puneet.blog.payload.CategoryDTO;
import com.puneet.blog.response.ApiResponse;
import com.puneet.blog.services.CategoryService;

@RestControllerAdvice
@RequestMapping("/api/v1/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService ;
	
	LocalDateTime localDateTime = LocalDateTime.now();
	
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO createCategory = categoryService.createCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(createCategory, HttpStatus.CREATED);
	}
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer catId) {
		CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, catId);
		return new ResponseEntity<CategoryDTO>(updatedCategory, HttpStatus.OK);
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
		categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted successfully", true, localDateTime), HttpStatus.OK);
	}
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer catId) {
		CategoryDTO categoryDTO = categoryService.getCatgeory(catId);
		return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		List<CategoryDTO> allCategories = categoryService.getAllCategories();
		return ResponseEntity.ok(allCategories);
	}
	

}
