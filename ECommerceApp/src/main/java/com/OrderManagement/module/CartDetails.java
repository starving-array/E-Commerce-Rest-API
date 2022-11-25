package com.OrderManagement.module;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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

	
	@Min(value = 1, message = "Min qty should be 1")
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
