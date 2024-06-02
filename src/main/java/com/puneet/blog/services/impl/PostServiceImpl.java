package com.puneet.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.puneet.blog.entites.Category;
import com.puneet.blog.entites.Post;
import com.puneet.blog.entites.User;
import com.puneet.blog.exceptions.ResourceNotFoundException;
import com.puneet.blog.payload.PostDTO;
import com.puneet.blog.repository.CategoryRepository;
import com.puneet.blog.repository.PostRepository;
import com.puneet.blog.repository.UserRepository;
import com.puneet.blog.response.PostResponse;
import com.puneet.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		Post post = modelMapper.map(postDTO, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = postRepository.save(post);
		return modelMapper.map(newPost, PostDTO.class);
	}

	@Override
	public PostDTO updatedPost(PostDTO postDTO, Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));
		Category category = categoryRepository.findById(postDTO.getCategory().getCategoryId()).get();
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImageName(postDTO.getImageName());
		post.setCategory(category);
		Post updatedPost = postRepository.save(post);
		PostDTO updatedPostDTO = modelMapper.map(updatedPost, PostDTO.class);
		return updatedPostDTO;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));
		postRepository.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable page = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = postRepository.findAll(page);
		List<Post> allPosts = pagePost.getContent();
		List<PostDTO> postDTOs = allPosts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDTOs);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));
		PostDTO postDTO = modelMapper.map(post, PostDTO.class);
		return postDTO;
	}

	@Override
	public List<PostDTO> getPostsByCategory(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts = postRepository.findByCategory(category);
		List<PostDTO> postDTOs = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDTOs;
	}

	@Override
	public List<PostDTO> getPostsByUser(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		List<Post> posts = postRepository.findByuser(user);
		List<PostDTO> postDTOs = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDTOs;
	}

	@Override
	public List<PostDTO> searchPosts(String keyword) {
		List<Post> posts = postRepository.findByTitleContaining(keyword);
		List<PostDTO> postDTOs = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDTOs;
	}

}
