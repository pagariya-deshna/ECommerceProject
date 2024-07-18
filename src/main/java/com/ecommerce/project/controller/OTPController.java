package com.ecommerce.project.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.model.Customer;
import com.ecommerce.project.repository.CustomerRepository;
import com.ecommerce.project.serviceImpl.OTPService;
import com.ecommerce.project.serviceImpl.OTPStore;
import com.ecommerce.project.util.OTPUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "eCommerceProject")
public class OTPController {
	 private final OTPService otpService = new OTPService();
	 
	 @Autowired
	 private OTPStore otpStore;
	 
	 @Autowired
	 private CustomerRepository customerRepository;

	    @PostMapping("/resendOTP/{emailId}")
	    public String generateOTP(@PathVariable String emailId) {
	    	Customer customer = null;
	    	try {
	    		 customer = customerRepository.findByEmailId(emailId);	
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error : " + e.getMessage());
			}
	    	
	    	if(customer != null) {
	        String otp = OTPUtil.generateOTP();
	        OTPStore.storeOTP(emailId, otp);
	        otpService.sendOTP(emailId, otp);
	        customer.setOtp(otp);
	        customer.setOtpExpirationTime(LocalDateTime.now().plusMinutes(1));
	        try {
	        	 customerRepository.save(customer);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error 2 : " + e.getMessage());
			}
	       
	        return "OTP generated and sent to user " + emailId;
	    	}else {
	    		return "Invalid email!";
	    	}
	    }

	    @PostMapping("/verify/{emailId}")
	    public String verifyOTP(@PathVariable String emailId, @RequestParam String otp) {
	    	boolean isValid = otpStore.verifyOTP(emailId, otp);
	        if (isValid) {
	            return "OTP verified successfully.";
	        } else {
	            return "Invalid OTP.";
	        }
	    }

}
