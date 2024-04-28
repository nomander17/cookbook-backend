package com.major.cookbook.dto;

import java.time.LocalDateTime;

public class CommentDTO {
	
	private int postId;
	private int userId;
	private Integer commentId;
	private String text;
    private LocalDateTime time;
    private byte[] image;
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
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public CommentDTO() {
		
	}
	public CommentDTO(int postId, int userId, Integer commentId, String text, LocalDateTime time, byte[] image) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.commentId = commentId;
		this.text = text;
		this.time = time;
		this.image = image;
	}
}
