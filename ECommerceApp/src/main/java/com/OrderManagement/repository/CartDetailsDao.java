package com.OrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrderManagement.module.CartDetails;

public interface CartDetailsDao extends JpaRepository<CartDetails, Integer> {

}
