package com.ecommerce.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.Customer;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

//	List<Address> findByAddressContaining(Customer customer1);
	
	Address findByCustomer_id(Long id);

}
