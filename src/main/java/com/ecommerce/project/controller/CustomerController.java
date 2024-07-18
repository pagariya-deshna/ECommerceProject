package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.request.AuthResponseDto;
import com.ecommerce.project.request.ChangePasswordRequest;
import com.ecommerce.project.request.ForgetPasswordRequest;
import com.ecommerce.project.request.LoginRequest;
import com.ecommerce.project.request.RegistrationRequest;
import com.ecommerce.project.request.UpdateRequest;
import com.ecommerce.project.response.RegistrationResponse;
import com.ecommerce.project.security.CustomUserDetailsService;
import com.ecommerce.project.security.JWTGenerator;
import com.ecommerce.project.service.RegistrationService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.ecommerce.project.request.AuthResponseDto;

@RestController 
@SecurityRequirement(name = "eCommerceProject")
public class CustomerController {

	
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTGenerator jwtGenerator;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@PostMapping(value = "/registerNewCustomer")
	public ResponseEntity<Object> customerRegistration(@RequestBody RegistrationRequest registrationRequest) {
		RegistrationResponse customer = registrationService.addCustomer(registrationRequest);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<AuthResponseDto> customerLogin(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmailId(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
	}
	
	@PutMapping(value="/updateCustomer/{emailId}")
	public ResponseEntity<Object> updateCustomerDetails(@PathVariable String emailId,@RequestBody UpdateRequest updateRequest) {
		RegistrationResponse customer = registrationService.updateCustomer(emailId,updateRequest);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/deleteCustomer/{id}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
		RegistrationResponse customer = registrationService.deleteCustomer(id);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@GetMapping(value="/getAllCustomers")
	public ResponseEntity<Object> getCustomerDetails() {
		RegistrationResponse customer = registrationService.getAllCustomer();
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@PostMapping(value = "/changePassword")
	public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
		RegistrationResponse newPassword = registrationService.changePassword(changePasswordRequest);
		return new ResponseEntity<>(newPassword, HttpStatus.OK);

	}

	@PostMapping("/forgotPassword")
	public ResponseEntity<Object> forgetPassword(@RequestBody ForgetPasswordRequest forgetePasswordRequest) {
		RegistrationResponse newPassword = registrationService.forgotPassword(forgetePasswordRequest);
		return new ResponseEntity<>(newPassword, HttpStatus.OK);
	}

}
