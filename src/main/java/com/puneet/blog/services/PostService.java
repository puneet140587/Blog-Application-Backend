package com.puneet.blog.services;

import java.util.List;

import com.puneet.blog.payload.PostDTO;
import com.puneet.blog.response.PostResponse;

public interface PostService {
	
	PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) ;
	
	PostDTO updatedPost(PostDTO postDTO, Integer postId) ;
	
	void deletePost(Integer postId) ;
	
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) ;
	
	PostDTO getPostById(Integer postId) ;
	
	List<PostDTO> getPostsByCategory(Integer categoryId) ;
	
	List<PostDTO> getPostsByUser(Integer userId) ;
	
	List<PostDTO> searchPosts(String keyword) ;
	

}
