package com.OrderManagement.module.address;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.OrderManagement.module.Orders;
import com.OrderManagement.module.User;

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
	private String name;
	private String contact;
	private String houseNo; // dao search
	private String city;
	private String state;
	private String country;
	private String landmark;
	
	
	@ManyToOne
	@JoinColumn(name = "PIN", referencedColumnName = "id")
	private PostalCodes postalCode; // dao search
	
	@ManyToOne
	@JoinColumn(name="User_id", referencedColumnName = "userId")
	private User user;
	
	@OneToOne
	private Orders orders;

}
