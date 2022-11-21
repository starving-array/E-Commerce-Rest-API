package com.OrderManagement.Admin.service;

import com.OrderManagement.Admin.module.CardCredential;
import com.OrderManagement.exceptions.AdminExpectation;

public interface CardService {
	public CardCredential updatePin(Long cardNumber, Integer cardPin, Integer newPin)
			throws AdminExpectation;
	
	public Double checkBalance(Long carNumber, Integer pardPin) throws AdminExpectation;
}
