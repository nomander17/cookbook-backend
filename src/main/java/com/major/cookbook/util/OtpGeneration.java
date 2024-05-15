package com.major.cookbook.util;

import java.util.Random;

public class OtpGeneration {
	public static String generateOtp() {
	    Random random = new Random();
	    int otpLength = 6; // 6-digit OTP
	    StringBuilder otp = new StringBuilder();
	    for (int i = 0; i < otpLength; i++) {
	        otp.append(random.nextInt(10)); // Generate random digit (0-9)
	    }
	    return otp.toString();
	}
}
