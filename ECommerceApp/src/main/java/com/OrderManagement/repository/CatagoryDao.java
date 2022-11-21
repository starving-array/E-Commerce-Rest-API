package com.OrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.module.Catagory;

public interface CatagoryDao extends JpaRepository<Catagory, Integer> {

	@Query("select c from Catagory c where categoryName=?1")
	public Catagory findbyname(String name);

}
