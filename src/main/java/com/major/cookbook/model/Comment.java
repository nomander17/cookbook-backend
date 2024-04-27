package com.major.cookbook.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
	
	@ManyToOne
	@JoinColumn(name = "postID")
	private Post post;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userID")
	private User userID;
	
	@Column(name="time")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime time;
	
	@Column(name="text", length=1000,nullable=false)
	private String text;
	
	@Lob
	@Column(name="image", columnDefinition="BLOB")
	private byte[] image;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="commentID")
	private List<Like> like = new ArrayList<>();

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public List<Like> getLike() {
		return like;
	}

	public void setLike(List<Like> like) {
		this.like = like;
	}

	
	public Comment() {
	}

	public Comment(Integer commentID, Post post, User userID, LocalDateTime time, String text, byte[] image,
			List<Like> like) {
		this.commentID = commentID;
		this.post = post;
		this.userID = userID;
		this.time = time;
		this.text = text;
		this.image = image;
		this.like = like;
	}
	
}
