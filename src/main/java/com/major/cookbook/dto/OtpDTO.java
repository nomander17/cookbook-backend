package com.major.cookbook.dto;

public class OtpDTO {
	private String otp;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	public OtpDTO(String otp) {
		this.otp = otp;
	}

	public OtpDTO() {
		
	}
	
	@Override
	public String toString() {
		return otp;
	}
}
