package com.major.cookbook.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="likes")
public class Like {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="likeID")
	private Integer likeID;
	
	@ManyToOne
	@JoinColumn(name = "postID")
	private Post post;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="commentID")
	private Comment commentID;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userID")
	private User userID;
	
	@Column(name="time")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime time;

	public Integer getLikeID() {
		return likeID;
	}

	public void setLikeID(Integer likeID) {
		this.likeID = likeID;
	}

	public Post getPostID() {
		return post;
	}

	public void setPostID(Post postID) {
		this.post = postID;
	}

	public Comment getCommentID() {
		return commentID;
	}

	public void setCommentID(Comment commentID) {
		this.commentID = commentID;
	}

	public User getUserID() {
		return userID;
	}

	public void setUserID(User userID) {
		this.userID = userID;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
		
	
}
