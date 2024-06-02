package com.puneet.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.puneet.blog.entites.Comment;
import com.puneet.blog.entites.Post;
import com.puneet.blog.exceptions.ResourceNotFoundException;
import com.puneet.blog.payload.CommentDTO;
import com.puneet.blog.repository.CommentRepository;
import com.puneet.blog.repository.PostRepository;
import com.puneet.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepository postRepository ;
	
	@Autowired
	private CommentRepository commentRepository ;
	
	@Autowired
	private ModelMapper modelMapper ;
	

	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post Id",postId));
		Comment comment = modelMapper.map(commentDTO, Comment.class);
		comment.setPost(post);
		Comment savedComment = commentRepository.save(comment) ;
		return modelMapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","comment Id",commentId)) ;
		commentRepository.delete(comment);
	}

}
