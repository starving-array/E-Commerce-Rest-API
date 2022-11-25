package com.OrderManagement.module.address;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostalCodes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
//	@Min(value = 6, message = "postalcode should be 6 digit")
//	@Max(value = 6, message = "postalcode should be 6 digit")
	private Integer PINCODE;
	
	@JsonIgnore
	private boolean active;
	
	@JsonIgnore
	@OneToMany(mappedBy = "postalCode", cascade = CascadeType.ALL)
	List<Addresses> addresses = new ArrayList<>();
}
