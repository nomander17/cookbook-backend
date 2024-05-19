package com.major.cookbook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.major.cookbook.model.Comment;
import com.major.cookbook.model.Like;
import com.major.cookbook.model.Post;

public interface LikeRepo extends JpaRepository<Like, Integer>{

	//Checking if a post is already liked
	@Query("SELECT i FROM Like i WHERE i.user.id=:userId AND i.post.id=:postId")
	public Like alreadyLikedPost(@Param("userId")int userId,@Param("postId")int postId);
	/*public Like findByUserIdAndPostId(int userId, int postId);*/
	
	//Checking if a comment is already liked
	@Query("SELECT i from Like i WHERE i.user.id=:userId AND i.comment.id=:commentId")
	public Like alreadyLikedComment(@Param("userId")int userId, @Param("commentId")int commentId);
	/*public Like findByUserIdAndCommentId(int userId, int commentId);*/
	
	//To get all likes from post
	public List<Like> findByPost(Optional<Post> post);

	// To get all likes for a comment
	public List<Like> findByComment(Optional<Comment> comment);

	// Get all posts liked by an user by ID
	@Query("SELECT l.post FROM Like l WHERE l.user.id = :userId AND l.post IS NOT NULL")
	public List<Post> findPostsLikedByUserId(@Param("userId") int userId);

}
