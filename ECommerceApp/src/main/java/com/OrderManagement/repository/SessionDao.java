package com.OrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrderManagement.module.CurrentSession;


public interface SessionDao extends JpaRepository<CurrentSession, Integer> {

	public CurrentSession findByUuid(String uuid);

}