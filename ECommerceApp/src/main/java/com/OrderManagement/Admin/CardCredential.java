package com.OrderManagement.Admin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private Double Balance;
	
	// this is a temp database for Card/ Credit/debit
	// Dummy for testing
	
	
}
