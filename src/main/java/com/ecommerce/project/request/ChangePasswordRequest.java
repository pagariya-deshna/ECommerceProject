package com.ecommerce.project.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
	private String emailId;
	private String oldPassword;
	private String newPassword;
	private String confirmNewPassword;

}
