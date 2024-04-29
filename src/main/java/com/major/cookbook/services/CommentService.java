package com.major.cookbook.services;

import java.util.List;

import com.major.cookbook.model.Comment;
import com.major.cookbook.model.Post;

public interface CommentService {

	//Create a new comment
	public Comment createComment(Comment comment);		
		
	//GET ALL comments under a specific postID
	public List<Comment> getCommentsForPost(int postID);
		
	//GET a specific comment by postID and commentID
	public Comment getCommentById(int postID, int commentId);	
	
	//Update a specific comment
	public Comment updateComment(Comment updatedComment);

	//Delete a particular comment by PostID and commentID
	public Comment deleteCommentByPostAndId(int postID, int commentId);

	//Delete a comment by its ID
	public Comment deleteCommentById(int commentId);

	public List<Comment> getAllComments();

}
