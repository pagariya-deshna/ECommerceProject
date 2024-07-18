package com.ecommerce.project.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.project.model.Brand;
import com.ecommerce.project.model.Product;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

	Boolean existsByBrandName(String brandName);

	Brand findByBrandName(String brandName);
	
//	List<Brand> findByOrdersContaining(Product product1);

	

}
