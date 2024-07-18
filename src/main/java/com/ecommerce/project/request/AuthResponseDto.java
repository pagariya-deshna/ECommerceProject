package com.ecommerce.project.request;

import lombok.Data;

@Data
public class AuthResponseDto {
	private String accessToken;
	private String tokenType="Bearer ";
	
	public AuthResponseDto(String token) {
		this.accessToken=token;
	}
}
