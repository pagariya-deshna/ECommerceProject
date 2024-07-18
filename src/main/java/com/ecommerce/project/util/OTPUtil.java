package com.ecommerce.project.util;

import java.security.SecureRandom;

public class OTPUtil {
	 private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	    private static final int OTP_LENGTH = 6;
	    private static final SecureRandom RANDOM = new SecureRandom();

	    public static String generateOTP() {
	        StringBuilder otp = new StringBuilder(OTP_LENGTH);
	        for (int i = 0; i < OTP_LENGTH; i++) {
	            otp.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
	        }
	        return otp.toString();
	    }

}
