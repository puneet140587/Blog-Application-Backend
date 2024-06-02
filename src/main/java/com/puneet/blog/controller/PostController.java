package com.puneet.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.puneet.blog.config.AppConstant;
import com.puneet.blog.payload.PostDTO;
import com.puneet.blog.response.ApiResponse;
import com.puneet.blog.response.PostResponse;
import com.puneet.blog.services.FileService;
import com.puneet.blog.services.PostService;

@RestController
@RequestMapping("/api/v1")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	LocalDateTime localDateTime = LocalDateTime.now();

	// Create a Post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO PostDTO, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDTO createPost = postService.createPost(PostDTO, userId, categoryId);
		return new ResponseEntity<PostDTO>(createPost, HttpStatus.CREATED);
	}

	// Get Post by User
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Integer userId) {
		List<PostDTO> posts = postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}

	// Get Post by Category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Integer categoryId) {
		List<PostDTO> posts = postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}

	// Get All Posts
	@GetMapping("/allposts")
	public ResponseEntity<PostResponse> fetchAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {
		PostResponse postResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	// Get Single Posts
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> fetchSinglePost(@PathVariable Integer postId) {
		PostDTO PostDTO = postService.getPostById(postId);
		return new ResponseEntity<PostDTO>(PostDTO, HttpStatus.OK);
	}

	// Delete a Post
	@DeleteMapping("/post/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		postService.deletePost(postId);
		return new ApiResponse("Post is successfully Deleted !!", true, localDateTime);
	}

	// Update a Post
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO PostDTO, @PathVariable Integer postId) {
		PostDTO updatedPost = postService.updatedPost(PostDTO, postId);
		return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
	}

	// Search Post by Title
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable String keyword) {
		List<PostDTO> result = postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
	}

	// Post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException {
		PostDTO PostDTO = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		PostDTO.setImageName(fileName);
		PostDTO updatePost = this.postService.updatedPost(PostDTO, postId);
		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);

	}

	// Method to serve files
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());

	}

}
