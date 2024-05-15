package com.major.cookbook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.major.cookbook.model.User;
import com.major.cookbook.security.JwtHelper;
import com.major.cookbook.util.OtpGeneration;

@Service
public class ResetServiceImpl implements ResetService {
	
	@Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JwtHelper jwtHelper;
    
    @Autowired
    private UserService userService;

	@Override
	public String generateJwt(String email) {
		// Generate OTP logic (e.g., random 6-digit OTP)
        String otp = OtpGeneration.generateOtp();
        User user = userService.getUserByEmail(email);
        String username = user.getUsername();
        // Generate JWT token with OTP and email
        String token = jwtHelper.generateTokenWithOtp(username, otp);

        // Send OTP via email
        sendEmail(email, otp, token);
        return token;
	}

	@Override
	public boolean verifyOtp(String token, String otp) {
		 return jwtHelper.verifyOtpFromToken(token, otp);
	}

	@Override
	public void sendEmail(String email, String otp, String token) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP for Password Reset");
        message.setText("Your OTP for resetting password for Cookbook is: " + otp);
        javaMailSender.send(message);
	}

}
