package com.OrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.module.Rating;

public interface RatingDao extends JpaRepository<Rating, Integer>{
	
	@Query("select c from Rating c where User_Id=?1")
	public Rating findByUser_Id(Integer userid);

}
