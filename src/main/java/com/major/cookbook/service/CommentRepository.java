package com.major.cookbook.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.major.cookbook.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
