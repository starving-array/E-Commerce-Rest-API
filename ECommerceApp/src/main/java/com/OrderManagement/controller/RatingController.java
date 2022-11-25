package com.OrderManagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.Rating;
import com.OrderManagement.service.RatingService;

@RestController
@RequestMapping("/rating")
public class RatingController {

	@Autowired
	private RatingService ratingService;

	@CrossOrigin
	@PostMapping("/{sessionid}/{userid}/{productid}")
	public ResponseEntity<Rating> addRating(
			@PathVariable("sessionid") String sessionId,
			@PathVariable("userid") Integer userid, 
			@PathVariable("productid") Integer productId,
			@Valid @RequestBody Rating rating) throws LoginException, UserException, ProductException {
		Rating rating2 = ratingService.addFeedback(sessionId, userid, productId, rating);
		return new ResponseEntity<Rating>(rating2, HttpStatus.CREATED);
	}
}
