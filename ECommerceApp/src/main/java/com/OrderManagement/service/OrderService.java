package com.OrderManagement.service;

import java.util.List;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.OrderException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.Orders;
import com.OrderManagement.paymentMethods.CardFormat;

public interface OrderService {

	public List<Orders> placeCartOrderPertial(List<Integer> cartIds, String sessionId, CardFormat cardFormat,
			Integer usercartId) throws ProductException, LoginException, UserException;

	public List<Orders> placeAllCartOrder(String sessionId, CardFormat cardFormat)
			throws ProductException, LoginException, UserException;

	public Orders placeOrderById(String sessionId, CardFormat cardFormat, Integer productId, Integer quantity)
			throws UserException, LoginException, ProductException;

	public Orders viewOrderById(String sessionId, Integer orderId) throws UserException, LoginException, OrderException;

	public List<Orders> viewAllOrder(String sessionId) throws UserException, LoginException, OrderException;

}
