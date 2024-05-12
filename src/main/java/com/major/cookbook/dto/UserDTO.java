package com.major.cookbook.dto;

import jakarta.validation.constraints.NotNull;

public class UserDTO {
    private Integer userId;
    private String username;
    private String email;
    private String password;
    private String name;
    private byte[] avatar;
 
    // Constructors, getter, setters

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

	public Integer getUserId() {
		return userId;
	}

	public UserDTO() {
	}

	public UserDTO(Integer userId,@NotNull String username,@NotNull String email,@NotNull String password,@NotNull String name, byte[] avatar) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.name = name;
		this.avatar = avatar;
	}
}
