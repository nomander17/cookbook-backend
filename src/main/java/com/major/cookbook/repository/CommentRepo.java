package com.major.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.major.cookbook.model.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
