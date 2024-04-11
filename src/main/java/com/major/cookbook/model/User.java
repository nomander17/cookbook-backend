package com.major.cookbook.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="user")
public class User {
	@Id
	@Column(name="userID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer userID;
	
	@Column(name="userName", nullable=false)
	@NotNull
	private String userName;
	
	@NotNull
	@Column(name="email", nullable=false)
	private String email;
	
	@NotNull
	@Column(name="password", nullable=false)
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="userID")
	private List<Comment> comment=new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="userID")
	private List<Like> like=new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="userID")
	private List<Post> post=new ArrayList<>();
	
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<Comment> getComment() {
		return comment;
	}
	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
	public List<Like> getLike() {
		return like;
	}
	public void setLike(List<Like> like) {
		this.like = like;
	}
	public List<Post> getPost() {
		return post;
	}
	public void setPost(List<Post> post) {
		this.post = post;
	}
	public User()
	{
		
	}
	public User(int userID, String userName, String email, String password) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
	public Integer getId() {
		return userID;
	}
	public void setId(Integer userID) {
		this.userID = userID;
	}
	public String getName() {
		return userName;
	}
	public void setName(String userName) {
		this.userName = userName;
	}	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	@Override
	public String toString() {
		return "User [userID=" + userID + ", userName=" + userName + ", email=" + email + ", password=" + password + "]";
	}
}

