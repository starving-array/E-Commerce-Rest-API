package com.OrderManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.CurrentSession;
import com.OrderManagement.module.Feedback;
import com.OrderManagement.module.Products;
import com.OrderManagement.module.User;
import com.OrderManagement.repository.FeedbackDao;
import com.OrderManagement.repository.ProductDao;
import com.OrderManagement.repository.SessionDao;
import com.OrderManagement.repository.UserDao;

public class FeedbackServImpl implements FeedbackService {
	@Autowired
	private ProductDao pdao;

	@Autowired
	private SessionDao sessionDao;

	@Autowired
	private UserDao udao;

	@Autowired
	private FeedbackDao feedbackDao;

	@Override
	public Feedback addFeedback(String sessionId, Integer productId, Feedback feedback)
			throws LoginException, UserException, ProductException {
		// user valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		Optional<User> user = udao.findById(cur.getUserId());
		if (user.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		User userCurrent = user.get();

		// Product
		Optional<Products> pOptional = pdao.findById(productId);
		if (pOptional.isEmpty()) {
			throw new ProductException("Product not exist with id " + productId);
		}
		Products products = pOptional.get();

		Feedback newFeedback = new Feedback();
		newFeedback.setCreateDate(LocalDateTime.now());
		newFeedback.setFeedbackTitle(feedback.getFeedbackTitle());
		newFeedback.setFeedbackBody(feedback.getFeedbackBody());

		newFeedback.setUser(userCurrent); // FK
		userCurrent.getFeedbacks().add(newFeedback);

		newFeedback.setProducts(products); // FK
		products.getFeedbacks().add(newFeedback);// reverse

		return feedbackDao.save(newFeedback);
	}

	// patching
	@Override
	public Feedback modifyFeedback(String sessionId, Feedback feedback) throws LoginException, UserException {
		// user valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		Optional<User> user = udao.findById(cur.getUserId());
		if (user.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		User userCurrent = user.get();

		// feedback
		Optional<Feedback> fOptional = feedbackDao.findById(feedback.getId());
		if (fOptional.isEmpty()) {
			throw new UserException("Feedback not exits");
		}
		Feedback modifiedFeedback = fOptional.get();

		// match if feedbackid belong to user
		Feedback checkMatchFeedback = feedbackDao.getfeedbackByIdandUser(userCurrent.getUserId(), feedback.getId());
		if (checkMatchFeedback == null) {
			throw new UserException("This feedback doesn't belong to you");
		}

		if (feedback.getFeedbackTitle() != null) {
			modifiedFeedback.setFeedbackTitle(feedback.getFeedbackTitle());
		}
		if (feedback.getFeedbackBody() != null) {
			modifiedFeedback.setFeedbackBody(feedback.getFeedbackBody());
		}
		modifiedFeedback.setModifiedDate(LocalDateTime.now());

		return feedbackDao.save(modifiedFeedback);
	}

	@Override
	public Feedback deleteFeedback(String sessionId, Integer feedbackId) throws LoginException, UserException {
		// user valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		Optional<User> user = udao.findById(cur.getUserId());
		if (user.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		User userCurrent = user.get();

		// feedback
		Optional<Feedback> fOptional = feedbackDao.findById(feedbackId);
		if (fOptional.isEmpty()) {
			throw new UserException("Feedback not exits");
		}
		Feedback modifiedFeedback = fOptional.get();

		// match if feedbackid belong to user
		Feedback checkMatchFeedback = feedbackDao.getfeedbackByIdandUser(userCurrent.getUserId(), feedbackId);
		if (checkMatchFeedback == null) {
			throw new UserException("This feedback doesn't belong to you");
		}
		userCurrent.getFeedbacks().remove(modifiedFeedback);// remove from user
		modifiedFeedback.getProducts().getFeedbacks().remove(modifiedFeedback); // remove from product
		feedbackDao.delete(checkMatchFeedback); // deleted
		return modifiedFeedback;
	}

	@Override
	public List<Feedback> viewMyFeedback(String sessionId, Integer productId)
			throws LoginException, UserException, ProductException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		Optional<User> user = udao.findById(cur.getUserId());
		if (user.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		User userCurrent = user.get();

		// Product
		Optional<Products> pOptional = pdao.findById(productId);
		if (pOptional.isEmpty()) {
			throw new ProductException("Product not exist with id " + productId);
		}
		Products products = pOptional.get();
		
		List<Feedback> listoFeedbacks = feedbackDao.getFeedBackByUserProduct(userCurrent.getUserId(), productId);
		if(listoFeedbacks.size()==0) {
			throw new UserException("You haven't added any feedback yet");
		}

		return listoFeedbacks;
	}

}
