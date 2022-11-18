package com.OrderManagement.service;

import java.util.List;

import com.OrderManagement.DTO.CartDto;
import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.CartDetails;
import com.OrderManagement.module.Products;

public interface AddCartService {
	// session id is mandate for any function in cart

	public Products addToCartProducts(Integer productId, String sessionId, Integer quantity)
			throws UserException, ProductException, LoginException;

	public List<CartDto> viewCart(String sessionId) throws ProductException, LoginException, UserException;

	public CartDetails modifyCart(CartDetails carrDetails, String sessionId)
			throws ProductException, LoginException, UserException;

	public CartDetails deleteFromCart(Integer cartId, String sessionId)
			throws ProductException, LoginException, UserException;

	

	// pagination and sort
}
