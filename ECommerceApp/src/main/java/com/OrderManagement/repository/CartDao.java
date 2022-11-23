package com.OrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.module.CartDetails;
import com.OrderManagement.module.Usercart;

public interface CartDao extends JpaRepository<Usercart, Integer>{
	
	@Query("select c from CartDetails c where P_Id=?1")
	public CartDetails getByProduct(Integer productId);
}
