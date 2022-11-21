package com.OrderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.module.address.PostalCodes;

public interface PostalDao extends JpaRepository<PostalCodes, Integer>{

	@Query("select p from PostalCodes p where PINCODE=?1")
	public PostalCodes getByCode(Integer pincode);
}
