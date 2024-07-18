package com.ecommerce.project.request;

import lombok.Data;

@Data
public class ProductRequest {
	private String name;
	private Integer quantity;
	private Double pricePerProduct;
	private BrandRequest brand;
	
	

}
