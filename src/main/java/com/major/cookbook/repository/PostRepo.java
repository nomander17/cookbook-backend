package com.major.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.major.cookbook.model.Post;

public interface PostRepo extends JpaRepository <Post, Integer> {

}
