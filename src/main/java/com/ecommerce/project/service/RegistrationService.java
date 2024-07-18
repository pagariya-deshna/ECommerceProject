package com.ecommerce.project.service;

import com.ecommerce.project.request.ChangePasswordRequest;
import com.ecommerce.project.request.ForgetPasswordRequest;
import com.ecommerce.project.request.RegistrationRequest;
import com.ecommerce.project.request.UpdateRequest;
import com.ecommerce.project.response.RegistrationResponse;

public interface RegistrationService {

	public RegistrationResponse addCustomer(RegistrationRequest registrationRequest);

	public RegistrationResponse updateCustomer(String emailId,UpdateRequest updateRequest);
	
	public RegistrationResponse deleteCustomer(Long id);
	
	public RegistrationResponse getAllCustomer();

	public void sendOTP(String emailId, String otp);
		
	public RegistrationResponse forgotPassword(ForgetPasswordRequest forgetPasswordRequest);

	public RegistrationResponse changePassword(ChangePasswordRequest changePasswordRequest);

//	public RegistrationResponse deleteCustomerFromRoles(Long id);
}
