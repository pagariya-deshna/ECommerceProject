package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.ecommerce.project.request.BrandRequest;
import com.ecommerce.project.request.ProductRequest;
import com.ecommerce.project.response.RegistrationResponse;
import com.ecommerce.project.service.ProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "eCommerceProject")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping(value="/addProduct")
	public ResponseEntity<Object> addProduct(@RequestBody ProductRequest productRequest){
		RegistrationResponse product = productService.addProduct(productRequest);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@PutMapping(value="/updateProduct/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable Long id,@RequestBody ProductRequest productRequest){
		RegistrationResponse product = productService.updateProduct(id,productRequest);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/deleteProduct/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable Long id){
		RegistrationResponse product = productService.deleteProduct(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@GetMapping(value="/getAllProducts")
	public ResponseEntity<Object> getAllProducts(){
		RegistrationResponse product = productService.getAllProducts();
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	
	@PostMapping(value="/addBrands")
	public ResponseEntity<Object> addBrands(@RequestBody BrandRequest brandRequest){
		RegistrationResponse brand = productService.addBrand(brandRequest);
		return new ResponseEntity<>(brand,HttpStatus.OK);
	}
	
//	@PostMapping(value="/addProductImage",consumes = "multipart/form-data")
//	public ResponseEntity<Object> addProductImage(@RequestParam("image") MultipartFile image,@RequestParam("productName") String productName,@RequestParam("brandName") String brandName) {
//		RegistrationResponse uploadImage=productService.addProductImage(image,productName,brandName);
//		return new ResponseEntity<>(uploadImage,HttpStatus.OK);
//	}
//	
	
	

}
