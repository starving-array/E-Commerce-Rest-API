package com.OrderManagement.module;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSource {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Integer id;
	private String accountInfo; // can be card/upi, so on

	@OneToMany(mappedBy = "source", cascade = CascadeType.ALL)
	private List<Orders> orders = new ArrayList<>();

}
