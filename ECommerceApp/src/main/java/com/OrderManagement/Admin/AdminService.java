package com.OrderManagement.Admin;

import java.util.List;

import com.OrderManagement.DTO.OrderDto;
import com.OrderManagement.exceptions.AdminExpectation;
import com.OrderManagement.exceptions.UserException;

public interface AdminService {

	// CardCredential
	// add --------------------------------------
	public CardCredential addCardCredentials(String sessionId, Long cardNumber, Integer cardPin, Double balance)
			throws AdminExpectation;

	public CardCredential updatePin(String sessionId, Long cardNumber, Integer cardPin, Integer newPin)
			throws AdminExpectation;

	// balance
	// -------------------------------------------
	public CardCredential updateCredential(String sessionId, Long cardNumber, Integer cardPin, Double balance)
			throws AdminExpectation;

	// ------------ // move to user
	public CardCredential viewBalance(String sessionId, Long cardNumber, Integer cardPin, Double balance)
			throws AdminExpectation;

	
	// Orders ------------------
	// card no from paymentSource
	public List<OrderDto> viewOrdersPaidByCardNo(String sessionId, Long cardNumber) throws AdminExpectation;

	public List<OrderDto> viwOrderShipToPinCode(String sessionId, Integer pincode) throws AdminExpectation;

	public List<OrderDto> viewOrderBuyFromByPinCode(String sessionId, Integer pincode) throws AdminExpectation;
	
	public List<OrderDto> viewOrdersByCity(String sessionId, String city) throws AdminExpectation;
	
	public List<OrderDto> viewOrderByState(String sessionId, String state) throws AdminExpectation;
	
	
	
	public List<OrderDto> viewOrderByCustomer(String sessionId, Integer customerId) throws AdminExpectation, UserException;
	
	// catagories
	//add -------------------------------
	
	
	// Products
	// add ------------------------------
	
	// 

}
