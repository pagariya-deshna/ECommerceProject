package com.ecommerce.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.project.model.Customer;
import com.ecommerce.project.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);
	List<Role> findByCustomers_id(Long id);
//	Role findByUser_id(Long user_id);

//	List<Role> findByRoleContaining(Customer customer1);
//
//	
//	List<Role> findByCustomersContaining(Customer customer);
}
