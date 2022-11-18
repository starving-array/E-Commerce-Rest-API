package com.OrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrderManagement.module.Usercart;

public interface CartDao extends JpaRepository<Usercart, Integer>{
	
}
