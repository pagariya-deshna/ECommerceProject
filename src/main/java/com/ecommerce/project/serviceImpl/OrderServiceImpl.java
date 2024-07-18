package com.ecommerce.project.serviceImpl;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.model.AddToCart;
import com.ecommerce.project.model.Customer;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.repository.AddToCartRepository;
import com.ecommerce.project.repository.CustomerRepository;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.request.CartRequest;
import com.ecommerce.project.response.RegistrationResponse;
import com.ecommerce.project.service.OrderService;
import com.ecommerce.project.util.IConstants;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddToCartRepository addToCartRepository;

	@Override
	public RegistrationResponse addToCart(CartRequest cartRequest) {
		RegistrationResponse registrationResponse = new RegistrationResponse();
		AddToCart addToCart = new AddToCart();
		if (cartRequest != null) {
			Product oldProduct = productRepository.findByProductCode(cartRequest.getProductCode());
			Customer oldCustomer = customerRepository.findByCustomerId(cartRequest.getCustomerId());
			if (oldProduct != null && oldCustomer != null) {
				if (productRepository.findQuantityByProductCode(cartRequest.getProductCode()) < cartRequest
						.getQuantity() || cartRequest.getQuantity() == 0) {
					registrationResponse.setMessage(IConstants.UNSATISFIED_QUANTITY);
					registrationResponse.setStatus(IConstants.BAD_REQUEST);
					registrationResponse.setObject(oldProduct);
				} else {
					addToCart.setPrice(oldProduct.getPrice());
					addToCart.setProductCode(cartRequest.getProductCode());
					addToCart.setQuantity(cartRequest.getQuantity());
					Integer oldQuantity = productRepository.findQuantityByProductCode(cartRequest.getProductCode());
					Integer newQuantity = oldQuantity - cartRequest.getQuantity();
					oldProduct.setQuantity(newQuantity);
					productRepository.save(oldProduct);

					// Save the AddToCart entity and associate it with the customer
					addToCart = addToCartRepository.save(addToCart);
					oldCustomer.getOrders().add(addToCart);
					customerRepository.save(oldCustomer);

					AddToCart save = addToCartRepository.save(addToCart);
					registrationResponse.setMessage(IConstants.ITEM_ADDED);
					registrationResponse.setStatus(IConstants.OK);
					registrationResponse.setObject(save);
				}
			} else if(oldProduct==null){
				registrationResponse.setMessage(IConstants.PRODUCT_UNAVAILABLE);
				registrationResponse.setStatus(IConstants.BAD_REQUEST);
				registrationResponse.setObject("");
			}else if(oldCustomer==null) {
				registrationResponse.setMessage(IConstants.WRONG_DATA);
				registrationResponse.setStatus(IConstants.BAD_REQUEST);
				registrationResponse.setObject("");
			}
		} else {
			registrationResponse.setMessage(IConstants.DATA_NOT_FOUND);
			registrationResponse.setStatus(IConstants.BAD_REQUEST);
			registrationResponse.setObject("");
		}
		return registrationResponse;
	}

	@Override
	public RegistrationResponse deleteFromCart(Long id) {
		RegistrationResponse registrationResponse= new RegistrationResponse();
		Optional<AddToCart> product=addToCartRepository.findById(id);
		if(product!=null) {
		        AddToCart product1 = product.get();

		        // Fetch customers associated with this AddToCart item
		        List<Customer> customers = customerRepository.findByOrdersContaining(product1);

		        // Remove the association between AddToCart item and customers
		        for (Customer customer : customers) {
		            customer.getOrders().remove(product1);
		            customerRepository.save(customer);
		        }

		        // Delete the AddToCart item
		        addToCartRepository.deleteById(id);
		        registrationResponse.setMessage(IConstants.PRODUCT_REMOVED );
				registrationResponse.setStatus(IConstants.OK);
				registrationResponse.setObject("");
		} else {
			registrationResponse.setMessage(IConstants.PRODUCT_NOT_FOUND );
			registrationResponse.setStatus(IConstants.BAD_REQUEST);
			registrationResponse.setObject("");
		}
		return registrationResponse;
	}

//	@Override
//	public RegistrationResponse placeOrder(OrderRequest orderRequest) {
//		RegistrationResponse registrationResponse = new RegistrationResponse();
//		if (orderRequest != null) {
//			Order order = new Order();
//			order.setOrderNumber(UUID.randomUUID().toString());
//			List<CartRequest> cartRequest = orderRequest.getCartRequest();
////		      Order save=orderRequest.setCartRequest(cartRequest);
//
//			orderRepository.save(order);
//		} else {
//			registrationResponse.setMessage(IConstants.DATA_NOT_FOUND);
//			registrationResponse.setStatus(IConstants.BAD_REQUEST);
//			registrationResponse.setObject("");
//		}
//
//		return registrationResponse;
//	}

}
