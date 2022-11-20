package com.OrderManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.OrderException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.Orders;
import com.OrderManagement.paymentMethods.CardFormat;
import com.OrderManagement.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/cart/{sessionid}/{cartid}")
	public ResponseEntity<List<Orders>> placeCartOrder(@RequestParam List<Integer> cartIds,
			@PathVariable("sessionid") String sessionId, @RequestBody CardFormat cardFormat,
			@PathVariable("cartid") Integer usercartId) throws ProductException, LoginException, UserException {

		List<Orders> list = orderService.placeCartOrderPertial(cartIds, sessionId, cardFormat, usercartId);
		return new ResponseEntity<List<Orders>>(list, HttpStatus.OK);

	}

	@PostMapping("/allcart/{sessionid}")
	public ResponseEntity<List<Orders>> placeCartOrderAll(@PathVariable("sessionid") String sessionId,
			@RequestBody CardFormat cardFormat) throws ProductException, LoginException, UserException {
		List<Orders> list = orderService.placeAllCartOrder(sessionId, cardFormat);
		return new ResponseEntity<List<Orders>>(list, HttpStatus.OK);
	}

	@PostMapping("/{sessionid}/{productid}/{quantity}")
	public ResponseEntity<Orders> placeDirectOrder(@PathVariable("sessionid") String sessionId,
			@RequestBody CardFormat cardFormat, @PathVariable("productid") Integer productId,
			@PathVariable("quantity") Integer quantity) throws UserException, LoginException, ProductException {
		Orders orders = orderService.placeOrderById(sessionId, cardFormat, productId, quantity);
		return new ResponseEntity<Orders>(orders, HttpStatus.OK);
	}

	@GetMapping("/view/id:/{sessionid}/{orderid}")
	public ResponseEntity<Orders> viewOrderDetailById(@PathVariable("sessionid") String sessionId,
			@PathVariable("orderid") Integer orderId) throws UserException, LoginException, OrderException {
		Orders orders = orderService.viewOrderById(sessionId, orderId);
		return new ResponseEntity<Orders>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/viewall/{sessionid}")
	public ResponseEntity<List<Orders>> viewOrders(@PathVariable("sessionid") String sessionId)
			throws UserException, LoginException, OrderException{
		List<Orders> listOfOrders = orderService.viewAllOrder(sessionId);
		return new ResponseEntity<List<Orders>>(listOfOrders, HttpStatus.OK);
	}
}
