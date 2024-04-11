package com.major.cookbook.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.major.cookbook.model.Post;

public interface PostRepository extends JpaRepository <Post, Integer> {

}