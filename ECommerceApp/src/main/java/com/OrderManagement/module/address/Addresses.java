package com.OrderManagement.module.address;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import com.OrderManagement.module.Orders;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Addresses {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank
	@Size(max = 25,min = 3, message = "Name should be 3 to 25 char")
	private String name;
	
	@NotBlank
	@Size(max = 10,min = 10, message = "Phone should be 10 char")
	@NumberFormat
	private String contact;
	@NotBlank
	private String houseNo; // dao search

	@NotBlank
	private String city;

	@NotBlank
	private String state;
	
	@NotBlank
	private String country;
	
	@NotBlank
	private String landmark;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "PIN", referencedColumnName = "id")
	private PostalCodes postalCode; // dao search


	@JsonIgnore
	@OneToMany(mappedBy = "shippingAddress", cascade = CascadeType.ALL)
	private List<Orders> orders = new ArrayList<>();

}
