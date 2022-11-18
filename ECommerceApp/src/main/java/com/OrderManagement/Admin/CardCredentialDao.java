package com.OrderManagement.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardCredentialDao extends JpaRepository<CardCredential, Integer>{

	@Query("select c from CardCredential c where c.cardNumber=?1 and c.cardPin=?2")
	public CardCredential matchCard(Long cardNo, Integer pin);
}
