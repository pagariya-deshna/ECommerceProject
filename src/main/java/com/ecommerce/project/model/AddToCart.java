package com.ecommerce.project.model;

import java.util.List;

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
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="add_to_cart")
public class AddToCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String productCode; 
	private Double price;
	private Integer quantity;
//	@ManyToMany(mappedBy = "orders")
//	private List<Customer> customer;
	
//	@JsonIgnore
//	@ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
//	@JoinTable(name="add_to_cart_customer",joinColumns = @JoinColumn(name = "add_to_cart_id",referencedColumnName = "id"),
//	inverseJoinColumns = @JoinColumn(name="customer_id",referencedColumnName = "id"))
//	private List<AddToCart> customer;
	
}
