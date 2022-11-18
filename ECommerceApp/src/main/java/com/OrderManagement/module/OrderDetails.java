package com.OrderManagement.module;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderDetailsId;
	private Integer quantity;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "O_id", referencedColumnName = "orderId")
	private Orders orders;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "Product_Id", referencedColumnName = "productId")
	private Products products;
}
