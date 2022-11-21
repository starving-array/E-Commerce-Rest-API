package com.OrderManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.module.Orders;

public interface OrderDao extends JpaRepository<Orders, Integer> {

	@Query("select c from Orders c where User_Id=?1 and orderId=?2")
	public Orders findOrderById(Integer userID, Integer orderId);

	public List<Orders> findTop5ByOrderByOrderIdDesc();
	
	
	@Query(" select o from Orders o where source=(select c.id from PaymentSource c where accountInfo=?1)")
	public List<Orders> getOrderByPaymentSource(String cardInfo);
	
	
	
}
