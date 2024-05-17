package com.major.cookbook.dto;

public class ResetPasswordDTO {
	
	private String resetPassword;

	public String getResetPassword() {
		return resetPassword;
	}

	public void setResetPassword(String resetPassword) {
		this.resetPassword = resetPassword;
	}

	public ResetPasswordDTO(String resetPassword) {
		this.resetPassword = resetPassword;
	}
	 
	public ResetPasswordDTO() {
		
	}
	
	public String toString() {
		return resetPassword;
	}
}
