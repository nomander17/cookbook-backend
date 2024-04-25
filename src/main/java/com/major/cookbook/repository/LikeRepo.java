package com.major.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.major.cookbook.model.Like;

public interface LikeRepo extends JpaRepository<Like, Integer>{

}
