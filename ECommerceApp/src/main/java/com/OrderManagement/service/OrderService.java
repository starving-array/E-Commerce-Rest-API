package com.OrderManagement.service;

import java.util.List;

import com.OrderManagement.DTO.OrderDto;
import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.OrderException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.address.Addresses;

public interface OrderService {

	public List<OrderDto> placeCartOrderPertial(List<Integer> cartIds, 
			String sessionId, Integer userid,
			Long cardNo, Integer cardPin, 
			Integer usercartId, Addresses shipAddresses, Integer shipPinCode)
			throws ProductException, LoginException, UserException;

	public List<OrderDto> placeAllCartOrder(String sessionId, Integer userid, Long cardNo, Integer cardPin,
			Addresses shipAddresses, Integer shipPinCode) throws ProductException, LoginException, UserException;

	public OrderDto placeOrderById(String sessionId, Integer userid, Long cardNo, Integer cardPin, Integer productId,
			Integer quantity, Addresses shipAddresses, Integer shipPinCode)
			throws UserException, LoginException, ProductException;


	public OrderDto viewOrderById(String sessionId, Integer userid, Integer orderId)
			throws UserException, LoginException, OrderException;

	public List<OrderDto> viewAllOrder(String sessionId, Integer userid)
			throws UserException, LoginException, OrderException;

}
