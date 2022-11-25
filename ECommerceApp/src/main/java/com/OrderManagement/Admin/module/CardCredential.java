package com.OrderManagement.Admin.module;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CardCredential {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Long cardNumber;

	private Integer cardPin;

	@Min(value = 1000, message = "Min char 1000")
	private Double Balance;
	
	// this is a temp database for Card/ Credit/debit
	// Dummy for testing
	
	
}
