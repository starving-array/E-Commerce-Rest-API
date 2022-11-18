package com.OrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.Admin.CardCredential;
import com.OrderManagement.module.Orders;

public interface OrderDao extends JpaRepository<Orders, Integer> {

	
}
