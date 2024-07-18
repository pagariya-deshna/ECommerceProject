package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.request.CartRequest;
import com.ecommerce.project.response.RegistrationResponse;
import com.ecommerce.project.service.OrderService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "eCommerceProject")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping(value = "/addToCart")
	public ResponseEntity<Object> addToCart(@RequestBody CartRequest cartRequest){
		RegistrationResponse cart = orderService.addToCart(cartRequest);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/deleteFromCart/{id}")
	public ResponseEntity<Object> deleteFromCart(@PathVariable Long id){
		RegistrationResponse delete=orderService.deleteFromCart(id);
		return new ResponseEntity<>(delete,HttpStatus.OK);
	}

}
