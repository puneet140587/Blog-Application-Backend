package com.puneet.blog.services;

import com.puneet.blog.payload.CommentDTO;

public interface CommentService {
	
	CommentDTO createComment(CommentDTO commentDTO, Integer postId) ;
	
	void deleteComment(Integer commentId) ; 

}
