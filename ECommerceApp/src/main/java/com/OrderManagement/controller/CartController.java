package com.OrderManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.DTO.CartDto;
import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.CartDetails;
import com.OrderManagement.service.AddCartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private AddCartService addCartService;

	@PostMapping("{sessionid}/{productid}/{quantity}/{userid}")
	public ResponseEntity<CartDto> addToCart(@PathVariable("productid") Integer productId,
			@PathVariable("sessionid") String sessionId, @PathVariable("userid") Integer userid,
			@PathVariable("quantity") Integer quantity) throws UserException, ProductException, LoginException {
		CartDto products = addCartService.addToCartProducts(productId, sessionId, userid, quantity);
		return new ResponseEntity<CartDto>(products, HttpStatus.CREATED);
	}

	@GetMapping("/all/{sessionid}/{userid)")
	public ResponseEntity<List<CartDto>> viewCart(@PathVariable("sessionid") String sessionId,
			@PathVariable("userid") Integer userid) throws ProductException, LoginException, UserException {
		List<CartDto> list = addCartService.viewCart(sessionId, userid);
		return new ResponseEntity<List<CartDto>>(list, HttpStatus.OK);
	}

	@PatchMapping("/modify/{sessionid}/{userid}")
	public ResponseEntity<CartDto> modifyCart(@RequestBody CartDetails cartDetails,
			@PathVariable("sessionid") String sessionId, @PathVariable("userid") Integer userid)
			throws ProductException, LoginException, UserException {
		CartDto cartDetails2 = addCartService.modifyCart(cartDetails, sessionId, userid);
		return new ResponseEntity<CartDto>(cartDetails2, HttpStatus.OK);
	}

	@DeleteMapping("/remove/{cartid}/{sessionid}/{userid}")
	public ResponseEntity<CartDto> deleteFromCart(@PathVariable("cartid") Integer cartId,
			@PathVariable("sessionid") String sessionId, @PathVariable("userid") Integer userid)
			throws ProductException, LoginException, UserException {
		CartDto cartDetails = addCartService.deleteFromCart(cartId, sessionId, userid);
		return new ResponseEntity<CartDto>(cartDetails, HttpStatus.OK);
	}
}
