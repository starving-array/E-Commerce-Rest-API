package com.OrderManagement.service;

import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.User;

public interface UserService {

	public User createAccount(User user) throws UserException;
	
	public User updateAccount(User user, String sessionId) throws UserException, LoginException;
	
	
	

	
	
	

}
