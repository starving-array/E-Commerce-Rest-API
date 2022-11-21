package com.OrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrderManagement.module.PaymentSource;

public interface PaymentSourceDao extends JpaRepository<PaymentSource, Integer> {

	public PaymentSource findByAccountInfo(String accountInfo);
	
}
