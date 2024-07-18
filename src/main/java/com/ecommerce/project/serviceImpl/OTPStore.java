package com.ecommerce.project.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.project.model.Customer;
import com.ecommerce.project.repository.CustomerRepository;

@Service
public class OTPStore {
	private static final ConcurrentHashMap<String, String> otpStore = new ConcurrentHashMap<>();
	private static final long EXPIRY_DURATION = 1; // OTP valid for 5 minutes

	@Autowired
	private CustomerRepository customerRepository;

	public static void storeOTP(String emailId, String otp) {
		otpStore.put(emailId, otp);
		// Simulate expiry after a certain duration
		new Thread(() -> {
			try {
				TimeUnit.MINUTES.sleep(EXPIRY_DURATION);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			otpStore.remove(emailId);
		}).start();
	}
	
	
//	 private static final int EXPIRY_DURATION = 5; // OTP expiry duration in minutes
//	    private static final ConcurrentMap<String, String> otpStore = new ConcurrentHashMap<>();
//	    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//	    public static void storeOTP(String emailId, String otp) {
//	        otpStore.put(emailId, otp);
//	        // Schedule the removal of the OTP after EXPIRY_DURATION minutes
//	        scheduler.schedule(() -> otpStore.remove(emailId), EXPIRY_DURATION, TimeUnit.MINUTES);
//	    }
//
//	    // Retrieve OTP
//	    public static String getOTP(String emailId) {
//	        return otpStore.get(emailId);
//	    }
//
//	    // Shut down the scheduler properly when the application stops
//	    public static void shutdownScheduler() {
//	        scheduler.shutdown();
//	        try {
//	            if (!scheduler.awaitTermination(1, TimeUnit.MINUTES)) {
//	                scheduler.shutdownNow();
//	            }
//	        } catch (InterruptedException e) {
//	            scheduler.shutdownNow();
//	            Thread.currentThread().interrupt();
//	        }
//	    }

	public boolean verifyOTP(String emailId, String otp) {

		Optional<Customer> oldCustomer = customerRepository.findByEmailIdAndOtp(emailId, otp);
		if (oldCustomer.isPresent()) {
			Customer customer = oldCustomer.get();
			LocalDateTime otpExpirationTime = customer.getOtpExpirationTime();
	        LocalDateTime now = LocalDateTime.now();
	        
	        if (now.isBefore(otpExpirationTime)) {
	            customer.setOtp(otp);  // Update OTP if necessary
	            customerRepository.save(customer);  // Save the customer entity
	            return true;  // OTP is valid
			}else {
				return false;
			}
		} else {
			return false;
		}
	}

}
