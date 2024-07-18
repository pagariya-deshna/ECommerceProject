package com.ecommerce.project.service;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.request.BrandRequest;
import com.ecommerce.project.request.ProductRequest;
import com.ecommerce.project.response.RegistrationResponse;

public interface ProductService {
	public RegistrationResponse addProduct(ProductRequest productRequest);
	public RegistrationResponse updateProduct(Long id,ProductRequest productRequest);
	public RegistrationResponse deleteProduct(Long id);
	public RegistrationResponse getAllProducts();
//	public RegistrationResponse getProductsByBrand(String brandName);
	public RegistrationResponse addProductImage(MultipartFile image);
	public RegistrationResponse addBrand(BrandRequest brandRequest);
	
	
}
