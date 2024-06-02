package com.puneet.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.puneet.blog.entites.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
