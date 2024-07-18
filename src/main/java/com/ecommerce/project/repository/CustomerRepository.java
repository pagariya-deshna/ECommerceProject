package com.ecommerce.project.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.project.model.AddToCart;
import com.ecommerce.project.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer findByEmailId(String emailId);
	Customer findByCustomerId(String customerId);
	Optional<Customer> findByEmailIdAndOtp(String emailId, String otp) ;
	Customer deleteByEmailId(String emailId);
	List<Customer> findByOrdersContaining(AddToCart product1);
	
}
