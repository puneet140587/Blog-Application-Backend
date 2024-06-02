package com.puneet.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.puneet.blog.entites.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
