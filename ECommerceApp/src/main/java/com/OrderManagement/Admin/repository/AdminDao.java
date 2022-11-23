package com.OrderManagement.Admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.Admin.module.AdminCredential;
import com.OrderManagement.module.Orders;

public interface AdminDao extends JpaRepository<AdminCredential, Integer> {

	@Query("select o from Orders o where shippingAddress = (select a.id from Addresses a where PIN =(select p.id from PostalCodes p where PINCODE=?1) )")
	public List<Orders> viwOrderShipToPinCode(Integer pincode);
}
