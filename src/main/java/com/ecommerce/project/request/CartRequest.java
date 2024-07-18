package com.ecommerce.project.request;

import lombok.Data;

@Data
public class CartRequest {
	private String productCode;
	private Integer quantity;
	private String customerId;
}
