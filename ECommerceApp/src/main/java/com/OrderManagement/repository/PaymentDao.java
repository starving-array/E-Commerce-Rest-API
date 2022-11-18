package com.OrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrderManagement.module.Payment;

public interface PaymentDao extends JpaRepository<Payment, Integer> {

	public Payment findByMethodName(String methodName);
}
