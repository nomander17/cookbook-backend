package com.major.cookbook.jwt;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class JwtResponse {
	private String jwtToken;
	private String username;
	private int userId;
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public JwtResponse(){
		
	}
	public JwtResponse(String jwtToken, String username, int userId) {
		this.jwtToken = jwtToken;
		this.username = username;
		this.userId = userId;
	}
}
