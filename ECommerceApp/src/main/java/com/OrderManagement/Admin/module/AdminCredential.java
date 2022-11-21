package com.OrderManagement.Admin.module;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class AdminCredential {	
	@Id
	private final Integer mobileNo=0000000000;
	private final String password="1234567890";
}
