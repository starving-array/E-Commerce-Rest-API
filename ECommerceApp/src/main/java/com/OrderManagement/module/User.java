package com.OrderManagement.module;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.OrderManagement.module.address.Addresses;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;

	private String FirstName;
	private String LastName;

	private String contact;
	
	private String email;
	private String password;

	private LocalDateTime date_of_birth;
	
	@JsonIgnore
	private LocalDateTime registrationDate;
	@JsonIgnore
	private LocalDateTime dateModified;
	
//	private String city;
//	private String state;
//	private String country;
//
//	private Integer postalCode;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Addresses> orderAddresses = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Orders> orders = new ArrayList<>();

	@JsonIgnore
	@OneToOne(mappedBy = "userC", cascade = CascadeType.ALL)
	private Usercart cart;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Feedback> feedbacks = new ArrayList<>();
}
