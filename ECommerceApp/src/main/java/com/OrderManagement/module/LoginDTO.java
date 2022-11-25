package com.OrderManagement.module;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class LoginDTO {

	@NotBlank
	private String mobileNo;

	@NotBlank
	private String password;
}
