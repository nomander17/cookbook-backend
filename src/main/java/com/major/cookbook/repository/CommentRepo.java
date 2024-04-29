package com.major.cookbook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.major.cookbook.model.Comment;
import com.major.cookbook.model.Post;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

	//Returns a list of comments under a specific post
	public List<Comment> findByPost(Optional<Post> post);

	//Returns a Comment when it matches with the post object and commentId
	public Optional<Comment> findByPostAndCommentId(Optional<Post> post, int commentId);

}
