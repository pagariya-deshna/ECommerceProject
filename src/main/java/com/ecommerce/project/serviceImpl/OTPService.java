package com.ecommerce.project.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.ecommerce.project.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OTPService {
	
	@Autowired
	private CustomerRepository customerRepository;
//	@Autowired
//	private EmailSender emailSender;
//	
	 public void sendOTP(String emailId, String otp) {
	        // Replace this with actual email/SMS sending code

			final String EMAIL_ADDRESS = "deshnapagariya1010@outlook.com";
			final String PASSWORD = "1234@890";

			// Replace with the recipient email, subject, and message text
			String recipientEmail = emailId;
			String subject = "Verification OTP";
			String messageText = "OTP for verification is: "+otp+". This otp is valid for 1 minute .";

			boolean result =EmailSender.sendEmail(EMAIL_ADDRESS, PASSWORD, recipientEmail, subject, messageText);
			if (result) {
				log.info("Email sent successfully!!");
			} else {
				log.error("Failed to send email.");
			} }

	
//	 public static void main(String[] args) {
//		 EmailSender emailSender=new EmailSender();
//	        // Replace this with actual email/SMS sending code
//		 String subject="body";
//		 String messageText="hello";
////		 EmailSender.sendHTMLMessage("nileshpagariya.ngd@gmail.com", subject, messageText);
//		emailSender.sendHTMLMessage("nileshpagariya.ngd@gmail.com",subject,messageText);
//		System.out.println("Run");
//	}

}
