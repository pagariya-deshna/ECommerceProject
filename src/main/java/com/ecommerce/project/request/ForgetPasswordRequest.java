package com.ecommerce.project.request;

import lombok.Data;

@Data
public class ForgetPasswordRequest {
	
	private String emailId;
	private String newPassword;
	private String confirmPassword;

}
