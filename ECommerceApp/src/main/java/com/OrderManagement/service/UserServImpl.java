package com.OrderManagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.CurrentSession;
import com.OrderManagement.module.User;
import com.OrderManagement.module.Usercart;
import com.OrderManagement.repository.SessionDao;
import com.OrderManagement.repository.UserDao;

@Service
public class UserServImpl implements UserService {

	@Autowired
	private UserDao uDao;
	@Autowired
	private SessionDao sessionDao;
	@Autowired
	private UserDao udao;

	@Override
	public User createAccount(User user) throws UserException {
		User emailCheck = uDao.findByEmail(user.getEmail());
		if (emailCheck != null) {
			throw new UserException("Email already registered with us");
		}
		User phoneCheck = uDao.findByContact(user.getContact());
		if (phoneCheck != null) {
			throw new UserException("Phone number already registered with us");
		}

		// dateEntry to set
		LocalDate nowDate = LocalDate.now();
		user.setRegistrationDate(nowDate);

		/// create a cart for new user
		Usercart cart = new Usercart();
		user.setCart(cart);
		cart.setUserC(user);

		return uDao.save(user);
	}

	@Override
	public List<User> getAllUser() throws UserException {
		List<User> users = uDao.findAll();
		if (users.isEmpty()) {
			throw new UserException("No user in database");
		}
		return users;
	}

	@Override
	public User updateAccount(User user, String sessionId) throws UserException, LoginException {
		// session check
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to first");
		}
		Optional<User> activeUser = udao.findById(cur.getUserId());
		if (activeUser.isEmpty()) {
			throw new LoginException("Please login with your account");
		}
		// if new email already registered with another account
		// it will throw error
		User emailCheck = uDao.findByEmail(user.getEmail());
		if (emailCheck != null) {
			throw new UserException("Email already registered with us");
		}
		// if new phone already registered with another account
		// then throw error
		User phoneCheck = uDao.findByContact(user.getContact());
		if (phoneCheck != null) {
			throw new UserException("Phone number already registered with us");
		}

		User existUser = activeUser.get();
		if (user.getFirstName() != null) {
			existUser.setFirstName(user.getFirstName());
		}
		if (user.getLastName() != null) {
			existUser.setLastName(user.getLastName());
		}
		if (user.getContact() != null) {
			existUser.setContact(user.getContact());
		}
		if (user.getEmail() != null) {
			existUser.setEmail(user.getEmail());
		}
		if (user.getPassword() != null) {
			existUser.setPassword(user.getPassword());
		}
		if (user.getDate_of_birth() != null) {
			existUser.setDate_of_birth(user.getDate_of_birth());
		}
		// modification date
		existUser.setDateModified(LocalDate.now());

//		if (user.getCity() != null) {
//			existUser.setCity(user.getCity());
//		}
//		if (user.getState() != null) {
//			existUser.setState(user.getState());
//		}
//		if (user.getCountry() != null) {
//			existUser.setCountry(user.getCountry());
//		}
//		if (user.getPostalCode() != null) {
//			existUser.setPostalCode(user.getPostalCode());
//		}
//		

		return udao.save(existUser);
	}

}
