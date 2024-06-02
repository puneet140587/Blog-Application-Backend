package com.puneet.blog.response;

import java.util.List;

import com.puneet.blog.payload.PostDTO;

import lombok.Data;

@Data
public class PostResponse {
	
	private List<PostDTO> content ;
	private int pageNumber ;
	private int pageSize;
	private int totalPages ;
	private long totalElements ;
	private boolean lastPage ;
	

}
