package com.major.cookbook.dto;

import java.time.LocalDateTime;

public class LikeDTO {
	
	private int postId;
	private int userId;
	private int commentId;
	private int likeId;
	private LocalDateTime time;
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getLikeId() {
		return likeId;
	}
	public void setLikeId(int likeId) {
		this.likeId = likeId;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public LikeDTO() {
		
	}
	public LikeDTO(int postId, int userId, int commentId, int likeId, LocalDateTime time) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.commentId = commentId;
		this.likeId = likeId;
		this.time = time;
	}
}
