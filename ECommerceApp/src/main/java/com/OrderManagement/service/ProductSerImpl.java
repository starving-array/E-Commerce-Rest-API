package com.OrderManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.module.Products;
import com.OrderManagement.repository.ProductDao;
@Service
public class ProductSerImpl implements ProductService{
	
	@Autowired
	private ProductDao productDao;
	

	@Override
	public List<Products> getProductByName(String name) throws ProductException {
		List<Products> list = productDao.getProductByname(name);
		if(list.size()==0) {
			throw new ProductException("No product exists");
		}
		return list;
	}

	@Override
	public Products getProductById(Integer id) throws ProductException {
		Optional<Products> prOptional = productDao.findById(id);
		if(prOptional.isEmpty()) {
			throw new ProductException("No product exists");
		}
		return prOptional.get();
	}

	@Override
	public Products registerProduct(Products products) {
		return productDao.save(products);
	}

}
