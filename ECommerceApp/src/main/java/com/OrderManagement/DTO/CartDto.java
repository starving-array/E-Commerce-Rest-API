package com.OrderManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
		private String productName;
		private Double productPrice;
		private Integer quantity;
		
}
