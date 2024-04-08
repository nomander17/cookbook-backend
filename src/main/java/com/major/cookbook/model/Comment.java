package com.major.cookbook.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="comment")
public class Comment {
	@Id
	@Column(name="commentID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer commentID; 

	public Integer getCommentID() {
		return commentID;
	}

	public void setCommentID(Integer commentID) {
		this.commentID = commentID;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="postID")
	private Post postID;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userID")
	private User userID;
	
	@Column(name="time")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime time;
	
	//json for content
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="commentID")
	private List<Like> like = new ArrayList<>();
	
	public void setPostID(Post postID) {
		this.postID = postID;
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
	
	public List<Like> getLike() {
		return like;
	}

	public void setLike(List<Like> like) {
		this.like = like;
	}
	public Post getPostID() {
		return postID;
	}
	
	//json content	
}
