package com.OrderManagement.Admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.Admin.module.CardCredential;
import com.OrderManagement.Admin.service.AdminService;
import com.OrderManagement.exceptions.AdminExpectation;
import com.OrderManagement.exceptions.OtherException;
import com.OrderManagement.module.Catagory;
import com.OrderManagement.module.Orders;
import com.OrderManagement.module.Products;
import com.OrderManagement.service.ProductService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/addcard/{sessionid}")
	public ResponseEntity<CardCredential> addNewCard(@PathVariable("sessionid") String sessionId,
			@RequestBody CardCredential cardCredential) throws AdminExpectation {
		CardCredential cardCredential2 = adminService.addCardCredentials(sessionId, cardCredential);
		return new ResponseEntity<CardCredential>(cardCredential2, HttpStatus.CREATED);
	}

	@PatchMapping("/addbalance/{sessionid}/{cardno}/{balance}")
	public ResponseEntity<CardCredential> addNewCard(@PathVariable("sessionid") String sessionId,
			@PathVariable("cardno") Long cardNumber, @PathVariable("balance") Double balance) throws AdminExpectation {
		CardCredential cardCredential2 = adminService.addBalance(sessionId, cardNumber, balance);
		return new ResponseEntity<CardCredential>(cardCredential2, HttpStatus.CREATED);
	}

	@GetMapping("/bycard/{sessionid}/{cardno}")
	public ResponseEntity<List<Orders>> viewOrderByCardNo(@PathVariable("sessionid") String sessionId,
			@PathVariable("cardno") Long cardNumber) throws AdminExpectation {
		List<Orders> list = adminService.viewOrdersPaidByCardNo(sessionId, cardNumber);
		return new ResponseEntity<List<Orders>>(list, HttpStatus.OK);
	}

	// product
	// product
	@PostMapping("/product/new/{sessionid}")
	public ResponseEntity<Products> registerProduct(@PathVariable("sessionid") String sessionId,
			@RequestBody Products products, @PathVariable("catagoryid") Integer catagoryId)
			throws AdminExpectation, OtherException {
		Products products2 = adminService.registerProduct(sessionId, products, catagoryId);
		return new ResponseEntity<Products>(products2, HttpStatus.CREATED);
	}
	
	// catagory
	@PostMapping("/catagory/new/{sessionid}")
	public ResponseEntity<Catagory> registerCatagory(@PathVariable("sessionid") String sessionId,
			@RequestBody Catagory catagory) throws OtherException, AdminExpectation {
		Catagory catagory2 = adminService.addCatagory(sessionId, catagory);
		return new ResponseEntity<Catagory>(catagory2, HttpStatus.CREATED);
	}

}
