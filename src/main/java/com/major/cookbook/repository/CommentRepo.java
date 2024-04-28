package com.major.cookbook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.major.cookbook.model.Comment;
import com.major.cookbook.model.Post;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

	public List<Comment> findByPost(Optional<Post> post);

	public Optional<Comment> findByPostAndCommentId(Optional<Post> post, int commentID);

}
