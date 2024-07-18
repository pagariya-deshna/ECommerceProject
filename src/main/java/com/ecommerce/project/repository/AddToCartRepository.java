package com.ecommerce.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.project.model.AddToCart;

@Repository
public interface AddToCartRepository extends JpaRepository<AddToCart, Long> {
//	@Query("SELECT * FROM AddToCart p WHERE p.productCode = :productCode && p.customer=customerId")
//	AddToCart findByProductCodeandCustomer(DeleteFromCartRequest deleteFrom);

	AddToCart deleteByProductCode(String productCode);
	
//	@Query("SELECT p.quantity FROM AddToCart p WHERE p.productCode = :productCode && p.customer=customerId")
//	Integer findQuantityByProductCodeandCustomer(DeleteFromCartRequest deleteFromCart);
	
}
