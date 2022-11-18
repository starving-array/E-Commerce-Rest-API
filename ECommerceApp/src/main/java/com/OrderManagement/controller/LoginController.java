package com.OrderManagement.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.LoginDTO;
import com.OrderManagement.module.User;
import com.OrderManagement.service.LoginService;

@RestController
public class LoginController {

	// all to be added to main

	@Autowired
	private LoginService userLogin;
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<String> logInUser(@Valid @RequestBody LoginDTO dto) throws LoginException {

		String result = userLogin.logIntoAccount(dto);

		return new ResponseEntity<String>(result, HttpStatus.OK);

	}
	@CrossOrigin
	@PostMapping("/logout")
	public String logoutUser(@RequestParam(required = false) String key) throws LoginException {
		return userLogin.logOutFromAccount(key);

	}

//	@DeleteMapping("/delete")
//	public String deleteAccount(@RequestParam String key, Integer userId) throws LoginException, UserException {
//		return userLogin.deleteUser(key, userId);
//
//	}
	@CrossOrigin
	@GetMapping("/get")
	public User getAccountDetails(@RequestParam String key, Integer userId) throws LoginException, UserException {

		return userLogin.getUser(key, userId);

	}

}