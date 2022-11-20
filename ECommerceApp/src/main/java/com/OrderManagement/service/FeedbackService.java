package com.OrderManagement.service;

import java.util.List;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.Feedback;

public interface FeedbackService {

	public Feedback addFeedback(String sessionId, Integer productId, Feedback feedback)
			throws LoginException, UserException, ProductException;

	public Feedback modifyFeedback(String sessionId, Feedback feedback)
			throws LoginException, UserException, ProductException;

	public Feedback deleteFeedback(String sessionId, Integer feedbackId) throws LoginException, UserException;

	public List<Feedback> viewMyFeedback(String sessionId, Integer productId) throws LoginException, UserException, ProductException;
}
