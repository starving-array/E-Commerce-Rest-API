package com.OrderManagement.paymentMethods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFormFormat {
	// card credentials
	private Long cardNo;
	private Integer pin;
}
