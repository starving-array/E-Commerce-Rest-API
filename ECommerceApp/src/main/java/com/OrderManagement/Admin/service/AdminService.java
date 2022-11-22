package com.OrderManagement.Admin.service;

import java.util.List;

import com.OrderManagement.Admin.module.CardCredential;
import com.OrderManagement.exceptions.AdminExpectation;
import com.OrderManagement.exceptions.OtherException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.Catagory;
import com.OrderManagement.module.Orders;
import com.OrderManagement.module.Products;
import com.OrderManagement.module.User;
import com.OrderManagement.module.address.PostalCodes;

public interface AdminService {
	
	// user
	public List<User> getAllUser(String sessionId) throws UserException, AdminExpectation;
	
	// CardCredential
	// add --------------------------------------
	public CardCredential addCardCredentials(String sessionId, CardCredential cardCredential) throws AdminExpectation;

	// balance
	// -------------------------------------------
	public CardCredential addBalance(String sessionId, Long cardNumber, Double balance) throws AdminExpectation;

	// Orders ------------------
	// card no from paymentSource
	public List<Orders> viewOrdersPaidByCardNo(String sessionId, Long cardNumber) throws AdminExpectation;

	// pending
	public List<Orders> viwOrderShipToPinCode(String sessionId, Integer pincode) throws AdminExpectation;

	public List<Orders> viewOrderBuyFromByPinCode(String sessionId, Integer pincode) throws AdminExpectation;

	public List<Orders> viewOrdersByCity(String sessionId, String city) throws AdminExpectation;

	public List<Orders> viewOrderByState(String sessionId, String state) throws AdminExpectation;

	public List<Orders> viewOrderByCustomer(String sessionId, Integer customerId)
			throws AdminExpectation, UserException;

	
	// catagories
	// add ------------------------------
	public Catagory addCatagory(String sessionId, Catagory catagory) throws OtherException, AdminExpectation;


	
	// Products
	// add ------------------------------
	public Products registerProduct(String sessionId, Products products, Integer catagoryId)
			throws AdminExpectation, OtherException;
	
	// PINCODE
	public List<PostalCodes> addPostalCodes(List<PostalCodes> postalCodes);
	public List<PostalCodes> deactivatePostalCodes(List<PostalCodes> postalCodes);
	public List<PostalCodes> reactivePostalCodes(List<PostalCodes> postalCodes);
	
	
	
	
	//	report
	// count , sum , avg by product, by quater, by year

}
