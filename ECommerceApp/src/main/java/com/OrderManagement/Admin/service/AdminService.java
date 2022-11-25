package com.OrderManagement.Admin.service;

import java.util.List;

import com.OrderManagement.Admin.module.CardCredential;
import com.OrderManagement.DTO.OrderDto;
import com.OrderManagement.exceptions.AdminExpectation;
import com.OrderManagement.exceptions.OtherException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.Catagory;
import com.OrderManagement.module.Payment;
import com.OrderManagement.module.Products;
import com.OrderManagement.module.User;
import com.OrderManagement.module.address.PostalCodes;

public interface AdminService {

	// user
	public List<User> getAllUser(String sessionId) throws UserException, AdminExpectation;

	// CardCredential
	// add --------------------------------------
	public CardCredential addCardCredentials(String sessionId, CardCredential cardCredential) throws AdminExpectation;
	public Payment addPaymentMethod(String sessionId, String methodname) throws AdminExpectation, OtherException;
	public Payment reactivePaymentMethod(String sessionId, String methodname) throws AdminExpectation, OtherException;
	public Payment deactivePaymentMethod(String sessionId, String methodname) throws AdminExpectation, OtherException;
	// balance
	// -------------------------------------------
	public CardCredential addBalance(String sessionId, Long cardNumber, Double balance) throws AdminExpectation;

	// Orders ------------------
	// card no from paymentSource
	public List<OrderDto> viewOrdersPaidByCardNo(String sessionId, Long cardNumber) throws AdminExpectation;

	public List<OrderDto> viwOrderShipToPinCode(String sessionId, Integer pincode) throws AdminExpectation;

	// pending
	public List<OrderDto> viewOrdersByCity(String sessionId, String city) throws AdminExpectation;

	public List<OrderDto> viewOrderByState(String sessionId, String state) throws AdminExpectation;

	public List<OrderDto> viewOrderByCustomer(String sessionId, Integer customerId)
			throws AdminExpectation, UserException;

	// catagories
	// add ------------------------------
	public Catagory addCatagory(String sessionId, Catagory catagory) throws OtherException, AdminExpectation;
	
	public List<Catagory> getAllCategory() throws OtherException;
	// Products
	// add ------------------------------
	public Products registerProduct(String sessionId, Products products, Integer catagoryId)
			throws AdminExpectation, OtherException;

	// PINCODE
	public List<PostalCodes> addPostalCodes(String sessionId, List<PostalCodes> postalCodes) throws AdminExpectation;

	public List<PostalCodes> deactivatePostalCodes(String sessionId, List<PostalCodes> postalCodes)
			throws AdminExpectation;

	public List<PostalCodes> reactivePostalCodes(String sessionId, List<PostalCodes> postalCodes)
			throws AdminExpectation;

	// report
	// count , sum , avg by product, by quater, by year

}
