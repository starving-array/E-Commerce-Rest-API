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

	public CartDto addToCartProducts(Integer productId, String sessionId,  Integer userid,Integer quantity)
			throws UserException, ProductException, LoginException;

	public List<CartDto> viewCart(String sessionId, Integer userid) throws ProductException, LoginException, UserException;

	public CartDto modifyCart(CartDetails carrDetails, String sessionId,  Integer userid)
			throws ProductException, LoginException, UserException;

	public CartDto deleteFromCart(Integer cartId, String sessionId, Integer userid)
			throws ProductException, LoginException, UserException;

}
