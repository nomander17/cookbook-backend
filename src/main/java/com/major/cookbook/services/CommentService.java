package com.major.cookbook.services;

import java.util.List;

import com.major.cookbook.model.Comment;
import com.major.cookbook.model.Post;

public interface CommentService {

	//Create a new comment
	public Comment createComment(Comment comment);		
		
	//GET ALL comments under a specific postId
	public List<Comment> getCommentsForPost(int postId);
		
	//GET a specific comment by postId and commentId
	public Comment getCommentById(int postId, int commentId);	
	
	//Update a specific comment
	public Comment updateComment(Comment updatedComment);

	//Delete a particular comment by PostId and commentId
	public Comment deleteCommentByPostAndId(int postId, int commentId);

	//Delete a comment by its Id
	public Comment deleteCommentById(int commentId);

	public List<Comment> getAllComments();

}
