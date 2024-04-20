package com.major.cookbook.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.major.cookbook.model.User;

public interface UserDao extends JpaRepository<User, Integer> {

}
