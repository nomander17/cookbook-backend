package com.major.cookbook.services;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.major.cookbook.model.Like;

public interface LikeService {
	//To Add new like to a Post
	public Like addPostLike(Like like);
	
	//To Add new like to Comment
	public Like addCommentLike(Like like);
	
	//Checking if a post is already liked
	@Query("SELECT i FROM Like i WHERE i.user.id=:userId AND i.post.id=:postId")
	public Like alreadyLikedPost(@Param("userId")int userId,@Param("postId")int postId);
	
	//Checking if a comment is already liked
	@Query("SELECT i from Like i WHERE i.user.id=:userId AND i.comment.id=:commentId")
	public Like alreadyLikedComment(@Param("userId")int userId, @Param("commenrtId")int commentId);
	
	//To get all likes from posts
	@Query("SELECT i FROM Like i WHERE i.post.id=:postId")
	public List<Like> getPostLike(@Param("postId") int postId);
	
	//To get all likes from Comment
	@Query("SELECT i FROM Like i WHERE i.comment.id=:commentId")
	public List<Like> getCommentLike(@Param("commentId") int commentId);
}
