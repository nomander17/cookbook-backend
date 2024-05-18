package com.major.cookbook.dto;

public class ResetPasswordDTO {
	
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String resetPassword) {
		this.password = resetPassword;
	}

	public ResetPasswordDTO(String resetPassword) {
		this.password = resetPassword;
	}
	 
	public ResetPasswordDTO() {
		
	}
	
	public String toString() {
		return password;
	}
}
