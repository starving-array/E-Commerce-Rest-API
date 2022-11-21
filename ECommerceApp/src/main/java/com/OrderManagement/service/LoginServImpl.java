package com.OrderManagement.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrderManagement.Admin.module.AdminCredential;
import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.CurrentSession;
import com.OrderManagement.module.LoginDTO;
import com.OrderManagement.module.User;
import com.OrderManagement.repository.SessionDao;
import com.OrderManagement.repository.UserDao;

import net.bytebuddy.utility.RandomString;

@Service
public class LoginServImpl implements LoginService {

	@Autowired
	private UserDao uDao;

	@Autowired
	private SessionDao sDao;

	@Override
	public String logIntoAccount(LoginDTO dto) throws LoginException {

		User existingUser = uDao.findByContact(dto.getMobileNo());

		if (existingUser == null) {

			throw new LoginException("Please Enter a valid mobile number");

		}

		Optional<CurrentSession> validUserSessionOpt = sDao.findById(existingUser.getUserId());

		if (validUserSessionOpt.isPresent()) {

			throw new LoginException("User already Logged In with this number");

		}

		if (existingUser.getPassword().equals(dto.getPassword())) {

			String key = RandomString.make(7);

			CurrentSession currentUserSession = new CurrentSession(existingUser.getUserId(), key, LocalDateTime.now());

			sDao.save(currentUserSession);

			return currentUserSession.toString();
		} else
			throw new LoginException("Please Enter a valid password");

	}

	// **************************************************

	@Override
	public String logOutFromAccount(String key) throws LoginException {

		CurrentSession validUserSession = sDao.findByUuid(key);

		if (validUserSession == null) {
			throw new LoginException("User Not Logged In with this number");
		}

		sDao.delete(validUserSession);

		return "Logged Out..!!";

	}

	// **************************************************
//
//	@Override
//	public String deleteUser(String key, Integer userId) throws LoginException, UserException {
//		// TODO Auto-generated method stub
//
//		CurrentUserSession validUserSession = sDao.findByUuid(key);
//		Optional<User> validUser = uDao.findById(userId);
//
//		if (validUserSession == null) {
//			throw new LoginException("Please enter correct key..!!");
//
//		}
//		if (validUser.isEmpty()) {
//			throw new UserException("Please enter correct user id..!!");
//
//		}
//
//		if (validUserSession.getUserId() == validUser.get().getUserId()) {
//			sDao.delete(validUserSession);
//			uDao.delete(validUser.get());
//		}
//
//		return "Account deleted..!!";
//
//	}

	// **************************************************

	@Override
	public User getUser(String key, Integer userId) throws LoginException, UserException {
		// TODO Auto-generated method stub
		User user = new User();
		CurrentSession validUserSession = sDao.findByUuid(key);
		Optional<User> validUser = uDao.findById(userId);

		if (validUserSession == null) {
			throw new LoginException("Please enter correct key..!!");

		}
		if (validUser.isEmpty()) {
			throw new UserException("Please enter correct user id..!!");

		}

		if (validUserSession.getUserId() == validUser.get().getUserId()) {
			user = validUser.get();
			return user;
		}
		throw new UserException("Please enter correct user id..!!");

	}

	@Override
	public String loginAdmin(LoginDTO dto) throws LoginException {
		AdminCredential existingUser = new AdminCredential();

		if (existingUser == null) {
			throw new LoginException("Please Enter a valid mobile number");
		}

		// admin can login as many admin at same time

		if (existingUser.getPassword().equals(dto.getPassword())) {

			String key = RandomString.make(7);

			CurrentSession currentUserSession = new CurrentSession(existingUser.getMobileNo(), key,
					LocalDateTime.now());

			sDao.save(currentUserSession);

			return currentUserSession.toString();
		} else
			throw new LoginException("Please Enter a valid password");

	}

}
