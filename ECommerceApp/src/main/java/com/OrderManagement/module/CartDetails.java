package com.OrderManagement.module;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cartDetailId;
	private Integer quantity;

	@JsonIgnore
	private LocalDateTime productAddDate;
	
	
	@JsonIgnore
	private LocalDateTime modifyDate;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "CartId", referencedColumnName = "id")
	private Usercart usercart;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "P_Id", referencedColumnName = "productId")
	private Products productCart;
}
