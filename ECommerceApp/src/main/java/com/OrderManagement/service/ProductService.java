package com.OrderManagement.service;

import java.util.List;

import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.module.Products;

public interface ProductService {

	
	public List<Products> getProductByName(String name) throws ProductException;
	
	public Products getProductById(Integer id) throws ProductException;
	
	public Products registerProduct(Products products);
	
}
