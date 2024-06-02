package com.puneet.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.puneet.blog.entites.Category;
import com.puneet.blog.entites.Post;
import com.puneet.blog.entites.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	List<Post> findByuser(User user) ;
	List<Post> findByCategory (Category category) ;
	
	List<Post> findByTitleContaining(String title) ;

}
