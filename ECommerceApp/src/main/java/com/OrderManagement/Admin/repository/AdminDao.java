package com.OrderManagement.Admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.Admin.module.AdminCredential;
import com.OrderManagement.module.Orders;
import com.OrderManagement.module.address.PostalCodes;

public interface AdminDao extends JpaRepository<AdminCredential, Integer> {

	@Query("select o from Orders o where soid = (select a.id from Addresses a where PIN =(select p.id from PostalCodes p where PINCODE=?1) )")
	public List<Orders> viwOrderShipToPinCode(Integer pincode);

	@Query("select o from Orders o where soid = (select a.id from Addresses a where  city like %:city%)")
	public List<Orders> viwOrderByCity(String city);

	@Query("select o from Orders o where soid = (select a.id from Addresses a where  city like %:state%)")
	public List<Orders> viwOrderByState(String state);

	@Query("select o from Orders o where User_Id =?1")
	public List<Orders> viwOrderByUser(Integer userid);

}
