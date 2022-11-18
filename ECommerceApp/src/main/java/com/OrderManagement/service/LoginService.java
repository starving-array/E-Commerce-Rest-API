package com.OrderManagement.service;


import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.LoginDTO;
import com.OrderManagement.module.User;

public interface LoginService {
	public String logIntoAccount(LoginDTO dto) throws LoginException;

	public String logOutFromAccount(String key) throws LoginException;

//	public String deleteUser(String key, Integer userId)throws LoginException, UserException;

	public User getUser(String key, Integer userId) throws LoginException, UserException;
	
	public String loginAdmin(LoginDTO dto) throws LoginException;
}
