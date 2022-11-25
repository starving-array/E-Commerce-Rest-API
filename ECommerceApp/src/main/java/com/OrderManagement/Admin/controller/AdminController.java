package com.OrderManagement.Admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.Admin.module.CardCredential;
import com.OrderManagement.Admin.service.AdminService;
import com.OrderManagement.DTO.OrderDto;
import com.OrderManagement.exceptions.AdminExpectation;
import com.OrderManagement.exceptions.OtherException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.Catagory;
import com.OrderManagement.module.Payment;
import com.OrderManagement.module.Products;
import com.OrderManagement.module.User;
import com.OrderManagement.module.address.PostalCodes;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	// user
	@CrossOrigin
	@GetMapping("/getuser/{sessionid}")
	public ResponseEntity<List<User>> getAllUser(@PathVariable("sessionid") String sessionId)
			throws UserException, AdminExpectation {
		List<User> list = adminService.getAllUser(sessionId);
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}

	// money
	@CrossOrigin
	@PostMapping("/addcard/{sessionid}")
	public ResponseEntity<CardCredential> addNewCard(
			@PathVariable("sessionid") String sessionId,
			@Valid @RequestBody CardCredential cardCredential) throws AdminExpectation {
		CardCredential cardCredential2 = adminService.addCardCredentials(sessionId, cardCredential);
		return new ResponseEntity<CardCredential>(cardCredential2, HttpStatus.CREATED);
	}

	@CrossOrigin
	@PatchMapping("/addbalance/{sessionid}/{cardno}/{balance}")
	public ResponseEntity<CardCredential> addBalance(@PathVariable("sessionid") String sessionId,
			@PathVariable("cardno") Long cardNumber, @PathVariable("balance") Double balance) throws AdminExpectation {
		CardCredential cardCredential2 = adminService.addBalance(sessionId, cardNumber, balance);
		return new ResponseEntity<CardCredential>(cardCredential2, HttpStatus.CREATED);
	}

	// order
	@CrossOrigin
	@GetMapping("/orderbycard/{sessionid}/{cardno}")
	public ResponseEntity<List<OrderDto>> viewOrderByCardNo(@PathVariable("sessionid") String sessionId,
			@PathVariable("cardno") Long cardNumber) throws AdminExpectation {
		List<OrderDto> list = adminService.viewOrdersPaidByCardNo(sessionId, cardNumber);
		return new ResponseEntity<List<OrderDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/bypostalcode/{sessionid}/{postal}")
	public ResponseEntity<List<OrderDto>> viewOrderByPostalCode(@PathVariable("sessionid") String sessionId,
			@PathVariable("postal") Integer pincode) throws AdminExpectation {
		List<OrderDto> list = adminService.viwOrderShipToPinCode(sessionId, pincode);
		return new ResponseEntity<List<OrderDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/orderfromcity/{sessionid}/{city}")
	public ResponseEntity<List<OrderDto>> viewOrderByCity(@PathVariable("sessionid") String sessionId,
			@PathVariable("city") String city) throws AdminExpectation {
		List<OrderDto> list = adminService.viewOrdersByCity(sessionId, city);
		return new ResponseEntity<List<OrderDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/orderfromstate/{sessionid}/{state}")
	public ResponseEntity<List<OrderDto>> viewOrderByState(@PathVariable("sessionid") String sessionId,
			@PathVariable("state") String state) throws AdminExpectation {
		List<OrderDto> list = adminService.viewOrderByState(sessionId, state);
		return new ResponseEntity<List<OrderDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/orderbyuser/{sessionid}/{userid}")
	public ResponseEntity<List<OrderDto>> viewOrderByCustomer(@PathVariable("sessionid") String sessionId,
			@PathVariable("userid") Integer userId) throws AdminExpectation, UserException {
		List<OrderDto> list = adminService.viewOrderByCustomer(sessionId, userId);
		return new ResponseEntity<List<OrderDto>>(list, HttpStatus.OK);
	}

	// product
	@CrossOrigin
	@PostMapping("/product/new/{sessionid}/{catagoryid}")
	public ResponseEntity<Products> registerProduct(@PathVariable("sessionid") String sessionId,
			@Valid @RequestBody Products products, @PathVariable("catagoryid") Integer catagoryId)
			throws AdminExpectation, OtherException {
		Products products2 = adminService.registerProduct(sessionId, products, catagoryId);
		return new ResponseEntity<Products>(products2, HttpStatus.CREATED);
	}

	// catagory
	@CrossOrigin
	@PostMapping("/catagory/new/{sessionid}")
	public ResponseEntity<Catagory> registerCatagory(@PathVariable("sessionid") String sessionId,
			@Valid @RequestBody Catagory catagory) throws OtherException, AdminExpectation {
		Catagory catagory2 = adminService.addCatagory(sessionId, catagory);
		return new ResponseEntity<Catagory>(catagory2, HttpStatus.CREATED);
	}

	@GetMapping("/catagory")
	public ResponseEntity<List<Catagory>> getAllCategory() throws OtherException{
		List<Catagory> catagoryas = adminService.getAllCategory();
		return new ResponseEntity<List<Catagory>>(catagoryas, HttpStatus.ACCEPTED.OK);
	}
	// pincode
	@CrossOrigin
	@PostMapping("/postalcode/add/{sessionid}")
	public ResponseEntity<List<PostalCodes>> addPostalCodes(@PathVariable("sessionid") String sessionId,
			@Valid @RequestBody List<PostalCodes> postalCodes) throws AdminExpectation {
		List<PostalCodes> list = adminService.addPostalCodes(sessionId, postalCodes);
		return new ResponseEntity<List<PostalCodes>>(list, HttpStatus.CREATED);
	}

	@CrossOrigin
	@PostMapping("/postalcode/deactive/{sessionid}")
	public ResponseEntity<List<PostalCodes>> deactivePostalCodes(@PathVariable("sessionid") String sessionId,
			@Valid @RequestParam List<PostalCodes> postalCodes) throws AdminExpectation {
		List<PostalCodes> list = adminService.deactivatePostalCodes(sessionId, postalCodes);
		return new ResponseEntity<List<PostalCodes>>(list, HttpStatus.CREATED);
	}

	@CrossOrigin
	@PostMapping("/postalcode/active/{sessionid}")
	public ResponseEntity<List<PostalCodes>> reactivePostalCodes(@PathVariable("sessionid") String sessionId,
			@Valid @RequestParam List<PostalCodes> postalCodes) throws AdminExpectation {
		List<PostalCodes> list = adminService.reactivePostalCodes(sessionId, postalCodes);
		return new ResponseEntity<List<PostalCodes>>(list, HttpStatus.CREATED);
	}
	
	// payment method
	@CrossOrigin
	@PostMapping("/paymentmethod/activate/{sessionid}/{method}")
	public ResponseEntity<Payment> addPaymentMethod(@PathVariable("sessionid") String sessionId,
			@PathVariable("method") String methodName) throws AdminExpectation, OtherException {
		Payment payment = adminService.addPaymentMethod(sessionId, methodName);
		return new ResponseEntity<Payment>(payment, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@PatchMapping("/paymentmethod/deactivate/{sessionid}/{method}")
	public ResponseEntity<Payment> reactivatePaymentMethod(@PathVariable("sessionid") String sessionId,@PathVariable("method") String methodName) throws AdminExpectation, OtherException{
		Payment payment =adminService.reactivePaymentMethod(sessionId, methodName);
		return new ResponseEntity<Payment>(payment, HttpStatus.CREATED);
	}
	

	@CrossOrigin
	@PatchMapping("/paymentmethod/{sessionid}/{method}")
	public ResponseEntity<Payment> deactivatePaymentMethod(@PathVariable("sessionid") String sessionId,@PathVariable("method") String methodName) throws AdminExpectation, OtherException{
		Payment payment =adminService.deactivePaymentMethod(sessionId, methodName);
		return new ResponseEntity<Payment>(payment, HttpStatus.CREATED);
	}

}
