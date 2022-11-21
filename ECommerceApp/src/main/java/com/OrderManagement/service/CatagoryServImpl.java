package com.OrderManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.OrderManagement.exceptions.OtherException;
import com.OrderManagement.module.Catagory;
import com.OrderManagement.repository.CatagoryDao;

public class CatagoryServImpl implements CatagoryService{

	@Autowired
	private CatagoryDao catagoryDao;
	
	@Override
	public List<Catagory> getAllCatagory() throws OtherException {
		List<Catagory> list = catagoryDao.findAll();
		if(list.size()==0) {
			throw new OtherException("No catagory exists");
		}
		return list;
	}

	

}
