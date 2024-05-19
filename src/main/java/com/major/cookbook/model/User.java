package com.major.cookbook.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User implements UserDetails{
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
    
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @Lob
    @Column(name = "avatar", columnDefinition = "MEDIUMBLOB")
    private byte[] avatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();
    
    @NotNull
    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin=false;


	public boolean getIsAdmin() {
		return isAdmin;
	}


	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public byte[] getAvatar() {
		return avatar;
	}


	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}


	@Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", name=" + name + ", email=" + email + ", password=" + password + "]";
    }

	
    // Constructors, getter, setters

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
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
			@NotNull String name, byte[] avatar, List<Comment> comments, List<Like> likes, List<Post> posts,
			@NotNull boolean isAdmin) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.name = name;
		this.avatar = avatar;
		this.comments = comments;
		this.likes = likes;
		this.posts = posts;
		this.isAdmin = isAdmin;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(this.getRole());
		return List.of(simpleGrantedAuthority);
	}

	
	private String getRole() {
		if(this.getIsAdmin()) {
			return "ROLE_ADMIN";
		}else
			return "ROLE_USER";
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public boolean isEnabled() {
		return true;
	}


	@Override
	public String getUsername() {
		return this.username;
	}
}
