package com.major.cookbook.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.major.cookbook.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
