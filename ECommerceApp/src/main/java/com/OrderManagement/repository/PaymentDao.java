package com.OrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.module.Payment;

public interface PaymentDao extends JpaRepository<Payment, Integer> {

	@Query("select c from Payment c where methodName=?1")
	public Payment findByMethodName(String methodName);
}
