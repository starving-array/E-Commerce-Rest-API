package com.OrderManagement.service;

import java.util.List;

import com.OrderManagement.exceptions.OtherException;
import com.OrderManagement.module.Catagory;

public interface CatagoryService {

	// view catagorylist
	public List<Catagory> getAllCatagory() throws OtherException;

}
