package com.OrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.module.Rating;

public interface RatingDao extends JpaRepository<Rating, Integer>{
	
	@Query("select c from Rating c where User_Id=?1 and Product_Id=?2")
	public Rating getByUserAndproduct(Integer userid, Integer productId);

}
