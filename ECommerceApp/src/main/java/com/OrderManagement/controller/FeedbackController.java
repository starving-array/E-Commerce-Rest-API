package com.OrderManagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.Feedback;
import com.OrderManagement.service.FeedbackService;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@CrossOrigin
	@PostMapping("/new/{sessionid}/{productid}/{userid}")
	public ResponseEntity<Feedback> addFeedBack(@PathVariable("sessionid") String sessionId,
			@PathVariable("userid") Integer userid, @PathVariable("productid") Integer productId,
			@Valid @RequestBody Feedback feedback) throws LoginException, UserException, ProductException {
		Feedback feedback2 = feedbackService.addFeedback(sessionId, userid, productId, feedback);
		return new ResponseEntity<Feedback>(feedback2, HttpStatus.CREATED);
	}

	@CrossOrigin
	@PatchMapping("/edit/{sessionid}/{userid}")
	public ResponseEntity<Feedback> modifyFeedback(@PathVariable("sessionid") String sessionId,
			@PathVariable("userid") Integer userid, @Valid @RequestBody Feedback feedback)
			throws LoginException, UserException, ProductException {
		Feedback feedback2 = feedbackService.modifyFeedback(sessionId, userid, feedback);
		return new ResponseEntity<Feedback>(feedback2, HttpStatus.OK);
	}

	@CrossOrigin
	@DeleteMapping("/del/{sessionid}/{feedid}/{userid}")
	public ResponseEntity<Feedback> addFeedBack(@PathVariable("sessionid") String sessionId,
			@PathVariable("userid") Integer userid, @PathVariable("feedid") Integer feedbackId)
			throws LoginException, UserException {
		Feedback feedback2 = feedbackService.deleteFeedback(sessionId, userid, feedbackId);
		return new ResponseEntity<Feedback>(feedback2, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/view/{sessionid}/{productid}/{userid}")
	public ResponseEntity<List<Feedback>> viewMyFeedBack(@PathVariable("sessionid") String sessionId,
			@PathVariable("userid") Integer userid, @PathVariable("productid") Integer productId)
			throws LoginException, UserException, ProductException {
		List<Feedback> list = feedbackService.viewMyFeedback(sessionId, userid, productId);
		return new ResponseEntity<List<Feedback>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/viewall/{productid}")
	public ResponseEntity<List<Feedback>> viewAllFeedBack(@PathVariable("productid") Integer productId)
			throws ProductException {
		List<Feedback> list = feedbackService.viewAllFeedback(productId);
		return new ResponseEntity<List<Feedback>>(list, HttpStatus.OK);
	}

}
