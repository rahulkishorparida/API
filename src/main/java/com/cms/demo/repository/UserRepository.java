package com.cms.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUsername(String username);
	

}
