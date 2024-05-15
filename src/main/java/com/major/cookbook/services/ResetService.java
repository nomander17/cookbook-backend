package com.major.cookbook.services;

public interface ResetService {
	
	//Sends OTP to email
	public String generateJwt(String email);
	
	//Verifies the OTP
	public boolean verifyOtp(String token, String otp);
	
	//Generates the Email
	public void sendEmail(String email, String otp, String token); 
}
