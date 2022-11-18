package com.OrderManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.module.Products;

public interface ProductDao extends JpaRepository<Products, Integer>{

	@Query("select c from Products c where productName like %:name%")
	public List<Products> getProductByname(String name);
}
