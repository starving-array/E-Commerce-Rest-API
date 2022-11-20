package com.OrderManagement.DTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OrderDto implements Serializable {

	private LocalDate orderDate;
	private Boolean prepaid;
	private Double total_order_amount;
	private Integer quantity;
	private String productName;
	private String Brand;
	private String categoryName;

	private Map<String, String> billingAddress = new HashMap<>();

	private Map<String, String> shippingAddress = new HashMap<>();

}
