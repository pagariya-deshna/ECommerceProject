package com.ecommerce.project.model;

import java.time.LocalDateTime;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	@NotBlank(message = "Please enter email id.")
	private String emailId;
	@NotBlank(message = "Please enter mobile number.")
	@Size(max = 10, message = "Please provide a valid mobile number !")
	private String mobileNumber;
	private String gender;
	private String password;
	private Boolean status;//true : active & false : inactive
	private LocalDateTime date;
	@Size(min=6,max=6)
	private String otp; 
	private LocalDateTime otpExpirationTime;


	 private String customerId;

	    @PrePersist
	    protected void onCreate() {
	        if (customerId == null) {
	            customerId = generateCustomCustomerId();
	        }
	    }

	    private String generateCustomCustomerId() {
	        // Generate a UUID and use the first part to create a shorter custom ID
	        String uuid = UUID.randomUUID().toString().split("-")[0]; // Use the first part of the UUID
	        return "CUS_" + uuid;
	    }

//	@Override
//	public int hashCode() {
//		return Objects.hash("CUS", customerId);
//	}
	    
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "customer_address", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id"))
	private Address address;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name="customer_add_to_cart",joinColumns = @JoinColumn(name = "customer_id",referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name="add_to_cart_id",referencedColumnName = "id"))
	private List<AddToCart> orders;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name="user_roles",joinColumns=@JoinColumn(name="customer_id",referencedColumnName =" id"),
	inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
	private List<Role> roles= new ArrayList<>();

}
