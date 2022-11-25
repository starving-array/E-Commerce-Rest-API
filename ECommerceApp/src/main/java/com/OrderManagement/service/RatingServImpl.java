package com.OrderManagement.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.CurrentSession;
import com.OrderManagement.module.Products;
import com.OrderManagement.module.Rating;
import com.OrderManagement.module.User;
import com.OrderManagement.repository.ProductDao;
import com.OrderManagement.repository.RatingDao;
import com.OrderManagement.repository.SessionDao;
import com.OrderManagement.repository.UserDao;

@Service
public class RatingServImpl implements RatingService {

	@Autowired
	private ProductDao pdao;

	@Autowired
	private SessionDao sessionDao;

	@Autowired
	private UserDao udao;

	@Autowired
	private RatingDao ratingDao;

	@Override
	public Rating addFeedback(String sessionId, Integer userid, Integer productId, Rating rating)
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
		// user verified

		// Product
		Optional<Products> pOptional = pdao.findById(productId);
		if (pOptional.isEmpty()) {
			throw new ProductException("Product not exist with id " + productId);
		}
		Products products = pOptional.get();

		Rating rating2 = ratingDao.getByUserAndproduct(userid, products.getProductId());

		if (rating2 == null) {
			rating2 = new Rating();
			rating2.setCreateDate(LocalDateTime.now());
			rating2.setRate(rating.getRate());

			Integer totalRatings = products.getProductRating() + rating.getRate();
			Integer avgRatings = totalRatings / (products.getRatings().size() + 1);

			rating2.setProducts(products);
			products.getRatings().add(rating2);
			products.setProductRating(avgRatings);

			rating2.setUser(userCurrent); // FK
			userCurrent.getRatings().add(rating2);
			rating2 = ratingDao.save(rating2);

		} else {
			rating2.setModifiedDate(LocalDateTime.now());
			Integer oldrating = rating2.getRate();
			rating2.setRate(rating.getRate());

			Integer totalRatings = products.getProductRating() + rating.getRate() - oldrating;
			Integer avgRatings = totalRatings / products.getRatings().size();
			products.setProductRating(avgRatings);
			rating2 = ratingDao.save(rating2);
		}

		return rating2;
	}

}
