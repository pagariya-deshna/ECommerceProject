package com.ecommerce.project.request;

import lombok.Data;

@Data
public class UpdateRequest {
	private String firstName;
	private String lastName;
	private String gender;
	private AddressRequest address;

}
