package com.major.cookbook.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

@Entity
@Table(name="post")
public class Post {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="postId")
	private Integer postID;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userID")
	private User userID;
	
	@Column(name="time")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime time;
	
	//json content
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="postID")
	private List<Like> like=new ArrayList<>();
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="postID")
	private List<Comment> comment = new ArrayList<>();
	
	
	public Integer getPostID() {
		return postID;
	}

	public void setPostID(Integer postID) {
		this.postID = postID;
	}

	public User getUserID() {
		return userID;
	}

	public void setUserID(User userID) {
		this.userID = userID;
	}

	public List<Like> getLike() {
		return like;
	}

	public void setLike(List<Like> like) {
		this.like = like;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
