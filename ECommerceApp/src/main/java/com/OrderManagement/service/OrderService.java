package com.OrderManagement.service;

import java.util.List;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.OrderException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.Orders;
import com.OrderManagement.module.address.Addresses;
import com.OrderManagement.module.address.PostalCodes;
import com.OrderManagement.paymentMethods.CardFormat;

public interface OrderService {

	public List<Orders> placeCartOrderPertial(List<Integer> cartIds, String sessionId, Integer userid,
			CardFormat cardFormat, Integer usercartId, Addresses billingAddress, Addresses shipAddresses,
			Integer billPincode, Integer shipPinCode) throws ProductException, LoginException, UserException;

	public List<Orders> placeAllCartOrder(String sessionId, Integer userid, CardFormat cardFormat,
			Addresses billingAddress, Addresses shipAddresses, Integer billPincode, Integer shipPinCode)
			throws ProductException, LoginException, UserException;

	public Orders placeOrderById(String sessionId, Integer userid, CardFormat cardFormat, Integer productId,
			Integer quantity, Addresses billingAddress, Addresses shipAddresses, Integer billPincode,
			Integer shipPinCode) throws UserException, LoginException, ProductException;

	public Orders viewOrderById(String sessionId, Integer userid, Integer orderId)
			throws UserException, LoginException, OrderException;

	public List<Orders> viewAllOrder(String sessionId, Integer userid)
			throws UserException, LoginException, OrderException;

}
