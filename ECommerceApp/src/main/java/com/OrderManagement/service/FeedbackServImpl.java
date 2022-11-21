package com.OrderManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
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
	public Feedback addFeedback(String sessionId, Integer userid, Integer productId, Feedback feedback)
			throws LoginException, UserException, ProductException {
		// user valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		//
		if (cur.getUserId() != userid) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> user = udao.findById(userid);
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
		
		int feedbackTotal = 0;
		if(products.getProductFeedBack() != null) {
			feedbackTotal = products.getProductFeedBack();
		}
		feedbackTotal++; 
		products.setProductFeedBack(feedbackTotal);
		
		return feedbackDao.save(newFeedback);
	}

	// patching
	@Override
	public Feedback modifyFeedback(String sessionId, Integer userid, Feedback feedback)
			throws LoginException, UserException {
		// user valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		//
		if (cur.getUserId() != userid) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> user = udao.findById(userid);
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
	public Feedback deleteFeedback(String sessionId, Integer userid, Integer feedbackId)
			throws LoginException, UserException {
		// user valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		//
		if (cur.getUserId() != userid) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> user = udao.findById(userid);
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
		Products products = modifiedFeedback.getProducts();
		
		userCurrent.getFeedbacks().remove(modifiedFeedback);// remove from user
		products.getFeedbacks().remove(modifiedFeedback); // remove from product
		products.setProductFeedBack(products.getProductFeedBack()-1);
		feedbackDao.delete(checkMatchFeedback); // deleted
		return modifiedFeedback;
	}

	@Override
	public List<Feedback> viewMyFeedback(String sessionId, Integer userid, Integer productId)
			throws LoginException, UserException, ProductException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		//
		if (cur.getUserId() != userid) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> user = udao.findById(userid);
		User userCurrent = user.get();

		// Product
		Optional<Products> pOptional = pdao.findById(productId);
		if (pOptional.isEmpty()) {
			throw new ProductException("Product not exist with id " + productId);
		}
		Products products = pOptional.get();

		List<Feedback> listoFeedbacks = feedbackDao.getFeedBackByUserProduct(userCurrent.getUserId(), productId);
		if (listoFeedbacks.size() == 0) {
			throw new UserException("You haven't added any feedback yet");
		}

		return listoFeedbacks;
	}

	@Override
	public List<Feedback> viewAllFeedback(Integer productId) throws ProductException {
		List<Feedback> list = feedbackDao.getFeedBackByProduct(productId);
		if(list.size()==0) {
			throw new ProductException("No feedback found for this product");
		}
		return list;
	}

}
