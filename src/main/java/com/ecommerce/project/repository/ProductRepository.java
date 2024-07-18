package com.ecommerce.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.project.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByName(String name);

	@Query("SELECT p.quantity FROM Product p WHERE p.productCode = :productCode")
	Integer findQuantityByProductCode(String productCode);
	Product findByProductCode(String productCode);

//	List<Product> findByOrdersContaining(Product product1);
	
//	List<Product> findByBrandName(String brandName);

}
