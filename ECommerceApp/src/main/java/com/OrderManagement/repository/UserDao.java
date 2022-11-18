package com.OrderManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.module.User;


public interface UserDao extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	public User findByContact(String contact);
	
	@Query("select c from User c where name like %:name%")
	public List<User> findByName(String name);


}