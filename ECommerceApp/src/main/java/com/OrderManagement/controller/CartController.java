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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.DTO.CartDto;
import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.CartDetails;
import com.OrderManagement.module.Products;
import com.OrderManagement.service.AddCartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private AddCartService addCartService;

	@PostMapping("{sessionid}/{productid}/{quantity}")
	public ResponseEntity<Products> addToCart(@PathVariable("productid") Integer productId,
			@PathVariable("sessionid") String sessionId, @PathVariable("quantity") Integer quantity)
			throws UserException, ProductException, LoginException {
		Products products = addCartService.addToCartProducts(productId, sessionId, quantity);
		return new ResponseEntity<Products>(products, HttpStatus.CREATED);
	}

	@GetMapping("/all/{sessionid}")
	public ResponseEntity<List<CartDto>> viewCart(@PathVariable("sessionid") String sessionId)
			throws ProductException, LoginException, UserException {
		List<CartDto> list = addCartService.viewCart(sessionId);
		return new ResponseEntity<List<CartDto>>(list, HttpStatus.OK);
	}

	@PatchMapping("/modify/{sessionid}")
	public ResponseEntity<CartDetails> modifyCart(@RequestBody CartDetails cartDetails,
			@PathVariable("sessionid") String sessionId) throws ProductException, LoginException, UserException {
		CartDetails cartDetails2 = addCartService.modifyCart(cartDetails, sessionId);
		return new ResponseEntity<CartDetails>(cartDetails2, HttpStatus.OK);
	}

	@DeleteMapping("/remove/{cartid}/{sessionid}")
	public ResponseEntity<CartDetails> deleteFromCart(@PathVariable("cartid") Integer cartId,
			@PathVariable("sessionid") String sessionId) throws ProductException, LoginException, UserException {
		CartDetails cartDetails = addCartService.deleteFromCart(cartId, sessionId);
		return new ResponseEntity<CartDetails>(cartDetails, HttpStatus.OK);
	}
}
