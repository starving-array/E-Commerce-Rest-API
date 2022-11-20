package com.OrderManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.module.Feedback;

public interface FeedbackDao extends JpaRepository<Feedback, Integer> {

	@Query("select c from Feedback where id=?1 and User_Id=?2")
	public Feedback getfeedbackByIdandUser(Integer id, Integer userid);

	@Query("select c from Feedback where User_Id=?1 and Product_Id=?2")
	public List<Feedback> getFeedBackByUserProduct(Integer userId, Integer productId);
}
