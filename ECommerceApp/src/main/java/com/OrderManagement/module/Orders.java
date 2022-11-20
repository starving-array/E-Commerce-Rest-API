package com.OrderManagement.module;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
		
	private LocalDate OrderDate;
	private Double total_order_amount;
	
	private Boolean prepaid;
	 

	@ManyToOne
	@JoinColumn(name = "Payment_Id", referencedColumnName = "id")
	private Payment payments;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "User_Id", referencedColumnName = "userId")
	private User customer;
	
	@JsonIgnore
	@OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
	private OrderDetails orderDetails;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "source", referencedColumnName = "id")
	private PaymentSource source;
	
	@OneToOne
	private Addresses billingAddress;
	
	@OneToOne
	private Addresses shippingAddress;
	
}
