package com.puneet.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.puneet.blog.entites.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
