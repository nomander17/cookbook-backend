package com.major.cookbook.services;

import java.util.List;

import com.major.cookbook.model.Comment;
import com.major.cookbook.model.Post;

public interface CommentService {

	//Create a new comment
	public Comment createComment(Comment comment);		
		
	//GET ALL comments under a specific post
	public List<Comment> getCommentsForPost(int postID);
		
	//GET a specific comment by ID
	public Comment getCommentById(int postID, int commentID);	
	
	//Update a specific comment
	public Comment updateComment(Comment updatedComment);

	//Delete a particular comment
	public Comment deleteCommentByPostAndId(int postID, int commentID);

}
