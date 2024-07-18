package com.ecommerce.project.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.project.model.Customer;
import com.ecommerce.project.repository.CustomerRepository;
//import com.ecommerce.project.model.Role;


@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private CustomerRepository customerRepository;
	
	/**
	 * @param userRepository
	 */
	
	public CustomUserDetailsService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		Customer customer= customerRepository.findByEmailId(emailId);
		if(customer==null) {
			throw new UsernameNotFoundException("User not found with email:"+emailId);
		}
		return new User(customer.getEmailId(), customer.getPassword(),mapRolesToAuthorities(customer.getRoles()));
//		return new User(customer.getEmailId(), customer.getPassword(),null);

	}
	
	private Collection<GrantedAuthority> mapRolesToAuthorities(List<com.ecommerce.project.model.Role> list){
		return list.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
