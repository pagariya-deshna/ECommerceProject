package com.ecommerce.project.serviceImpl;

import java.time.LocalDateTime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.Customer;
import com.ecommerce.project.repository.AddressRepository;
import com.ecommerce.project.repository.CustomerRepository;
import com.ecommerce.project.repository.RoleRepository;
import com.ecommerce.project.request.AddressRequest;
import com.ecommerce.project.request.ChangePasswordRequest;
import com.ecommerce.project.request.ForgetPasswordRequest;
import com.ecommerce.project.request.RegistrationRequest;
import com.ecommerce.project.request.UpdateRequest;
import com.ecommerce.project.response.RegistrationResponse;
import com.ecommerce.project.service.RegistrationService;
import com.ecommerce.project.serviceImpl.EmailSender;
import com.ecommerce.project.util.IConstants;
import com.ecommerce.project.util.OTPUtil;

import lombok.extern.slf4j.Slf4j;

import com.ecommerce.project.model.Role;

@Slf4j
@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private OTPService otpService;

	@Autowired
	private PasswordEncoder passwordEncoder;

//	@Autowired
//	private EmailSender emailSender;

	@Override
	public RegistrationResponse addCustomer(RegistrationRequest registrationRequest) {
		RegistrationResponse registrationResponse = new RegistrationResponse();

		if (registrationRequest != null) {
			Customer oldCustomer = customerRepository.findByEmailId(registrationRequest.getEmailId());
			if (oldCustomer != null) {
				registrationResponse.setMessage(IConstants.ID_ALREADY_EXISTS);
				registrationResponse.setStatus(IConstants.BAD_REQUEST);
				registrationResponse.setObject("");
				return registrationResponse;
			} else {
				Customer customer = new Customer();
				customer.setFirstName(registrationRequest.getFirstName());
				customer.setLastName(registrationRequest.getLastName());
				customer.setEmailId(registrationRequest.getEmailId());
				customer.setMobileNumber(registrationRequest.getMobileNumber());
				customer.setGender(registrationRequest.getGender());
				customer.setPassword(passwordEncoder.encode((registrationRequest.getPassword())));
				customer.setStatus(true);
				customer.setDate(LocalDateTime.now());
				Address address = new Address();
				AddressRequest addressRequest = registrationRequest.getAddress();
				address.setCity(addressRequest.getCity());
				address.setDistrict(addressRequest.getDistrict());
				address.setPincode(addressRequest.getPincode());
				address.setState(addressRequest.getState());

				customer.setAddress(address);

				// generate OTP logic
				String otp = OTPUtil.generateOTP();
				OTPStore.storeOTP(registrationRequest.getEmailId(), otp);
				customer.setOtp(otp);
				customer.setOtpExpirationTime(LocalDateTime.now().plusMinutes(1));
				sendOTP(registrationRequest.getEmailId(), otp);

				Role roles = roleRepository.findByName("USER").get();
				customer.setRoles(Collections.singletonList(roles));

				Customer save = customerRepository.save(customer);
				registrationResponse.setMessage(IConstants.DATA_ADDED);
				registrationResponse.setStatus(IConstants.OK);
				registrationResponse.setObject(save);

				return registrationResponse;

			}
		} else {
			registrationResponse.setMessage(IConstants.ID_NULL);
			registrationResponse.setStatus(IConstants.BAD_REQUEST);
			registrationResponse.setObject("");
			return registrationResponse;
		}
	}

	@Override
	public RegistrationResponse updateCustomer(String emailId, UpdateRequest updateRequest) {
		RegistrationResponse registrationResponse = new RegistrationResponse();

		if (!emailId.equalsIgnoreCase("") && updateRequest != null) {
			Customer customer = customerRepository.findByEmailId(emailId);
			if (customer != null) {
				customer.setFirstName(updateRequest.getFirstName());
				customer.setLastName(updateRequest.getLastName());
				customer.setGender(updateRequest.getGender());
				customer.setStatus(true);
				Address address = new Address();
				AddressRequest addressRequest = updateRequest.getAddress();
				address.setCity(addressRequest.getCity());
				address.setDistrict(addressRequest.getDistrict());
				address.setPincode(addressRequest.getPincode());
				address.setState(addressRequest.getState());
				customer.setAddress(address);
				Customer save = customerRepository.save(customer);
				registrationResponse.setMessage(IConstants.DATA_UPDATED);
				registrationResponse.setStatus(IConstants.BAD_REQUEST);
				registrationResponse.setObject("");
			} else {
				registrationResponse.setMessage(IConstants.DATA_NOT_FOUND);
				registrationResponse.setStatus(IConstants.BAD_REQUEST);
				registrationResponse.setObject("");
			}

		} else {
			registrationResponse.setMessage(IConstants.ID_NULL);
			registrationResponse.setStatus(IConstants.BAD_REQUEST);
			registrationResponse.setObject("");
		}

		return registrationResponse;
	}

	@Override
	public RegistrationResponse deleteCustomer(Long id) {
		RegistrationResponse registrationResponse = new RegistrationResponse();
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {

			Customer customers = customer.get();
			List<Role> roles = roleRepository.findByCustomers_id(id); // Assumes 'customers' is the field in Role entity
																		// referring to Customer entity
			// Dissociate or delete roles associated with the customer
			for (Role role : roles) {
				// Remove association between customer and role in join table
				role.getCustomers().remove(customers); // Assuming 'getCustomers()' returns a collection of customers
														// associated with the role
			}
			// Assuming there is a single address associated with the customer
			Address address = addressRepository.findByCustomer_id(id); // Assuming a method in AddressRepository to find
																		// address by customer ID
			if (address != null) {
				addressRepository.delete(address); // Or use another method to dissociate or delete the address entity
			}

			customerRepository.deleteById(id);
			registrationResponse.setMessage(IConstants.DATA_DELETED);
			registrationResponse.setStatus(IConstants.OK);
			registrationResponse.setObject("");
		} else {
			registrationResponse.setMessage(IConstants.ID_NOT_FOUND + ":" + id);
			registrationResponse.setStatus(IConstants.BAD_REQUEST);
			registrationResponse.setObject("");
		}
		return registrationResponse;
	}

	@Override
	public RegistrationResponse getAllCustomer() {
		RegistrationResponse registrationResponse = new RegistrationResponse();
		try {
			// Fetch all customers from the repository
			List<Customer> customerList = customerRepository.findAll();

			if (customerList != null && !customerList.isEmpty()) {
				// Data found
				registrationResponse.setMessage(IConstants.DATA_FOUND);
				registrationResponse.setStatus(IConstants.OK);
				registrationResponse.setObject(customerList);
			} else {
				// Data not found
				registrationResponse.setMessage(IConstants.DATA_NOT_FOUND);
				registrationResponse.setStatus(IConstants.BAD_REQUEST);
				registrationResponse.setObject(Collections.emptyList());
			}
		} catch (Exception e) {
			// Handle any exceptions
			registrationResponse.setMessage(IConstants.ERROR_OCCURRED);
			registrationResponse.setStatus(IConstants.INTERNAL_SERVER_ERROR);
			registrationResponse.setObject(Collections.emptyList());
			// Log the exception
			// log.error("Exception occurred while fetching customers: ", e);
		}

		return registrationResponse;
	}

	@Override
	public void sendOTP(String emailId, String otp) {
		// Replace this with actual email sending code
//		System.out.println("Sending OTP " + otp + " to user " + emailId);

//		 String subject="OTP for verification";
//		 String messageText=otp;
//		emailSender.sendHTMLMessage("nileshpagariya.ngd@gmail.com",subject,messageText);
//		
//		
//		log.info("Sending OTP " + otp + " to user " + emailId);

		final String EMAIL_ADDRESS = "deshnapagariya1010@outlook.com";
		final String PASSWORD = "1234@890";

		// Replace with the recipient email, subject, and message text
		String recipientEmail = emailId;
		String subject = "Verification OTP";
		String messageText = "OTP for verification is: "+otp+"This otp is valid for 1 minute .";

		boolean result = EmailSender.sendEmail(EMAIL_ADDRESS, PASSWORD, recipientEmail, subject, messageText);
		if (result) {
			log.info("Email sent successfully!!");
		} else {
			log.error("Failed to send email.");
		}

	}


	@Override
	public RegistrationResponse changePassword(ChangePasswordRequest changePasswordRequest) {
		RegistrationResponse registrationResponse = new RegistrationResponse();
		Customer customer = customerRepository.findByEmailId(changePasswordRequest.getEmailId());
		if (customer != null) {
			if (customer.getPassword().equals(changePasswordRequest.getOldPassword())) {
				if (changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())) {
					customer.setPassword(changePasswordRequest.getNewPassword());
					customerRepository.save(customer);
					registrationResponse.setMessage(IConstants.PASSWORD_CHANGED_SUCCESSFULLY);
					registrationResponse.setStatus(IConstants.OK);
					registrationResponse.setObject("");
				} else {
					registrationResponse.setMessage(IConstants.ENTER_PASSWORD_CORRECTLY);
					registrationResponse.setStatus(IConstants.BAD_REQUEST);
					registrationResponse.setObject("");
				}
			} else {
				registrationResponse.setMessage(IConstants.PASSWORD_MISMATCHED);
				registrationResponse.setStatus(IConstants.BAD_REQUEST);
				registrationResponse.setObject("");

			}
		} else {
			registrationResponse.setMessage(IConstants.ID_NOT_FOUND);
			registrationResponse.setStatus(IConstants.BAD_REQUEST);
			registrationResponse.setObject("");
		}
		return registrationResponse;
	}

	@Override
	public RegistrationResponse forgotPassword(ForgetPasswordRequest forgetPasswordRequest) {
		RegistrationResponse registrationResponse = new RegistrationResponse();
		Customer customer = customerRepository.findByEmailId(forgetPasswordRequest.getEmailId());
		if (customer != null) {
			if (forgetPasswordRequest.getNewPassword().equals(forgetPasswordRequest.getConfirmPassword())) {
				customer.setPassword(forgetPasswordRequest.getNewPassword());
				customerRepository.save(customer);
				registrationResponse.setMessage(IConstants.PASSWORD_CHANGED_SUCCESSFULLY);
				registrationResponse.setStatus(IConstants.OK);
				registrationResponse.setObject("");
			} else {
				registrationResponse.setMessage(IConstants.ENTER_PASSWORD_CORRECTLY);
				registrationResponse.setStatus(IConstants.BAD_REQUEST);
				registrationResponse.setObject("");
			}

		} else {
			registrationResponse.setMessage(IConstants.ID_NOT_FOUND);
			registrationResponse.setStatus(IConstants.BAD_REQUEST);
			registrationResponse.setObject("");
		}
		return registrationResponse;
	}

}
