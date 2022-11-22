package com.OrderManagement.service;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.Rating;

public interface RatingService {
	public Rating addFeedback(String sessionId, Integer userid, Integer productId, Rating rating)
			throws LoginException, UserException, ProductException;

}
