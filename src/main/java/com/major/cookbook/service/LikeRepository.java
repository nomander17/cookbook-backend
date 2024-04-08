package com.major.cookbook.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.major.cookbook.model.Like;

public interface LikeRepository extends JpaRepository<Like, Integer>{

}
