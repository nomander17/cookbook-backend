package com.major.cookbook.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "username", nullable = false, unique = true)
    @NotNull
    private String username;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();
    
    @ManyToMany
    private List<User> followers = new ArrayList<>();
    
    @ManyToMany
    private List<User> following = new ArrayList<>();
    
    @NotNull
    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin=false;

    public List<User> getFollowers() {
		return followers;
	}


	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}


	public List<User> getFollowing() {
		return following;
	}


	public void setFollowing(List<User> following) {
		this.following = following;
	}	


	public boolean getIsAdmin() {
		return isAdmin;
	}


	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	@Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", email=" + email + ", password=" + password + "]";
    }

	
    // Constructors, getter, setters

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public User() {
	}

	public User(Integer userId, @NotNull String username, @NotNull String email, @NotNull String password,
			List<Comment> comments, List<Like> likes, List<Post> posts) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.comments = comments;
		this.likes = likes;
		this.posts = posts;
	}
}
