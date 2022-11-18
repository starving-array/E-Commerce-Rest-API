package com.OrderManagement.paymentMethods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardFormat {
	private Long cardNo;
	private Integer pin;
}
