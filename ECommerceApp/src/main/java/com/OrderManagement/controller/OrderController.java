package com.OrderManagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.DTO.OrderDto;
import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.OrderException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.address.Addresses;
import com.OrderManagement.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@CrossOrigin
	@PostMapping("/cart/{sessionid}/{userid}/{cardno}/{cardpin}/{cartid}/{shippin}")
	public ResponseEntity<List<OrderDto>> placeCartOrder(
			@RequestParam List<Integer> cartIds,
			@PathVariable("sessionid") String sessionId, 
			@PathVariable("userid") Integer userid,
			@PathVariable("cardno")	Long cardNo, 
			@PathVariable("cardpin") Integer cardPin, 
			@PathVariable("cartid") Integer usercartId,
			@Valid	@RequestBody Addresses shipAddresses, 
			@PathVariable("shippin") Integer shipPinCode
			)
			throws ProductException, LoginException, UserException {

		List<OrderDto> list = orderService.placeCartOrderPertial(cartIds, sessionId, userid, cardNo, cardPin, usercartId,
				shipAddresses, shipPinCode);
		return new ResponseEntity<List<OrderDto>>(list, HttpStatus.OK);

	}

	@CrossOrigin
	@PostMapping("/allcart/{sessionid}/{userid}/{cardno}/{cardpin}/{shippin}")
	public ResponseEntity<List<OrderDto>> placeCartOrderAll(
			@PathVariable("sessionid") String sessionId,
			@PathVariable("userid") Integer userid, 
			@PathVariable("cardno")	Long cardNo, 
			@PathVariable("cardpin") Integer cardPin,
			@Valid @RequestBody Addresses shipAddresses, 
			@PathVariable("shippin") Integer shipPinCode
			)
			throws ProductException, LoginException, UserException {
		List<OrderDto> list = orderService.placeAllCartOrder(sessionId, userid, cardNo, cardPin, shipAddresses, shipPinCode);
		return new ResponseEntity<List<OrderDto>>(list, HttpStatus.OK);
	}


	@CrossOrigin
	@PostMapping("/addd/{sessionid}/{userid}/{cardno}/{cardpin}/{productid}/{quantity}/{shippin}")
	public ResponseEntity<OrderDto> placeDirectOrder(
			@PathVariable("sessionid") String sessionId,
			@PathVariable("userid") Integer userid,
			@PathVariable("cardno")	Long cardNo, 
			@PathVariable("cardpin") Integer cardPin,
			@PathVariable("productid") Integer productId, 
			@PathVariable("quantity") Integer quantity,
			@Valid @RequestBody Addresses shipAddresses,
			@PathVariable("shippin")Integer shipPinCode)
			throws UserException, LoginException, ProductException {

		OrderDto orders = orderService.placeOrderById(sessionId, userid, cardNo,cardPin, productId, quantity, shipAddresses, shipPinCode);
		return new ResponseEntity<OrderDto>(orders, HttpStatus.CREATED);
	}


	@CrossOrigin
	@GetMapping("/view/id:/{sessionid}/{orderid}/{userid}")
	public ResponseEntity<OrderDto> viewOrderDetailById(@PathVariable("sessionid") String sessionId,
			@PathVariable("userid") Integer userid, @PathVariable("orderid") Integer orderId)
			throws UserException, LoginException, OrderException {
		OrderDto orders = orderService.viewOrderById(sessionId, userid, orderId);
		return new ResponseEntity<OrderDto>(orders, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/viewall/{sessionid}/{userid}")
	public ResponseEntity<List<OrderDto>> viewOrders(@PathVariable("sessionid") String sessionId,
			@PathVariable("userid") Integer userid) throws UserException, LoginException, OrderException {
		List<OrderDto> listOfOrders = orderService.viewAllOrder(sessionId, userid);
		return new ResponseEntity<List<OrderDto>>(listOfOrders, HttpStatus.OK);
	}
}
