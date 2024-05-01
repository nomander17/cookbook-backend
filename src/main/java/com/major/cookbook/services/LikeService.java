package com.major.cookbook.services;

import java.util.List;

import com.major.cookbook.model.Like;

public interface LikeService {
	//To Add new like to a Post
	public Like addPostLike(Like like);
	
	//To Add new like to Comment
	public Like addCommentLike(Like like);

	//GET all likes
    public List<Like> getAllLikes();
    
    //GET all likes by post ID
    public List<Like> getLikesByPostId(int postId);

    //GET all likes by comment ID
	public List<Like> getLikesByCommentId(int commentId);

    //DELETE like by Like ID
    public Like deleteLikeById(int int1);

    //Update like
    public Like updateLike(Like like);

}
