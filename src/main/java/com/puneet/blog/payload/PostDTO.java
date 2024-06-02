package com.puneet.blog.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PostDTO {
	
	private String postId ;
	
	private String title ;
	
	private String content ;
	
	private String imageName ;
	
	private Date addedDate ;
	
	private CategoryDTO category ;
	
	private UserDTO user ;
	
//	private Set<CommentDTO> comments = new HashSet<>() ;
	
	private List<CommentDTO> comments=new ArrayList<>(); 
	

}
