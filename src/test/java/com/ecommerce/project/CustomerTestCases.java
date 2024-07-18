package com.ecommerce.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.Customer;
import com.ecommerce.project.repository.AddressRepository;
import com.ecommerce.project.repository.CustomerRepository;
import com.ecommerce.project.request.AddressRequest;
import com.ecommerce.project.request.RegistrationRequest;
import com.ecommerce.project.response.RegistrationResponse;
import com.ecommerce.project.service.RegistrationService;
import com.ecommerce.project.serviceImpl.RegistrationServiceImpl;
import com.ecommerce.project.util.IConstants;


@ExtendWith(MockitoExtension.class)
public class CustomerTestCases {
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private AddressRepository addressRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private RegistrationService registrationService = new RegistrationServiceImpl();


	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddCustomer() {
	RegistrationRequest registrationRequest=new  RegistrationRequest();
	AddressRequest addressRequest = new AddressRequest();
	registrationRequest.setFirstName("Deshna");
	registrationRequest.setLastName("Pagariya");
	registrationRequest.setEmailId("xyz.123@gmail.com");
	registrationRequest.setMobileNumber("0292922222");
	registrationRequest.setGender("F");
	passwordEncoder.encode((registrationRequest.getPassword()));
	addressRequest.setCity("Mumbai");
	addressRequest.setDistrict("Mumbai");
	addressRequest.setPincode("324455");
	addressRequest.setState("Maharashtra");
	registrationRequest.setAddress(addressRequest);
	
	Customer customer=new Customer();
	Address address=new Address();
	customer.setFirstName("Harry");
	customer.setLastName("Potter");
	customer.setEmailId("harry123@gmail.com");
	customer.setMobileNumber("9383222222");
	customer.setGender("M");
	customer.setPassword("world");
	customer.setStatus(true);
	customer.setDate(LocalDateTime.now());
	address.setCity("Ujjain");
	address.setDistrict("Ujjain");
	address.setPincode("457332");
	address.setState("M.P.");
	customer.setAddress(address);
	
	when(customerRepository.save(customer)).thenReturn(customer);

	// Act
	RegistrationResponse response = registrationService.addCustomer(registrationRequest);

	// Assert
	assertEquals(IConstants.OK, response.getStatus());
	}
}
