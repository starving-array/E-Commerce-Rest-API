package com.OrderManagement.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.User;
import com.OrderManagement.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService uService;

	@CrossOrigin
	@PostMapping("/new")
	public ResponseEntity<User> createAccountH(@Valid @RequestBody User user) throws UserException {
		User newUser = uService.createAccount(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}

	@CrossOrigin
	@PatchMapping("/update/{sessionId}")
	public ResponseEntity<User> updateAccountH(@RequestBody User user, @PathVariable("sessionId") String sessionId)
			throws UserException, LoginException {
		User newUser = uService.updateAccount(user, sessionId);
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}



	

}
