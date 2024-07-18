package com.ecommerce.project.service;

import com.ecommerce.project.request.CartRequest;

import com.ecommerce.project.response.RegistrationResponse;

public interface OrderService {
	
	public RegistrationResponse addToCart(CartRequest cartRequest);
//	public RegistrationResponse placeOrder(OrderRequest orderRequest);
//	public RegistrationResponse generateInvoice(InvoiceRequest invoiceRequst);
	public RegistrationResponse deleteFromCart(Long id);

}
