package com.OrderManagement.Admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.Admin.module.CardCredential;

public interface CardCredentialDao extends JpaRepository<CardCredential, Integer>{

	@Query("select c from CardCredential c where c.cardNumber=?1 and c.cardPin=?2")
	public CardCredential matchCard(Long cardNo, Integer pin);
	
	@Query("select c from CardCredential c where cardNumber=?1")
	public CardCredential checkIfCardExist(Long cardNo);
}
