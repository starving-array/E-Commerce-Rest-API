package com.OrderManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.Orders;
import com.OrderManagement.paymentMethods.CardFormat;
import com.OrderManagement.service.OrderService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

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
			@RequestBody CardFormat cardFormat) throws ProductException, LoginException, UserException{
		List<Orders> list = orderService.placeAllCartOrder(sessionId, cardFormat);
		return new ResponseEntity<List<Orders>>(list, HttpStatus.OK);
	}
}
