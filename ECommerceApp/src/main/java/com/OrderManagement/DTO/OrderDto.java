package com.OrderManagement.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.OrderManagement.module.Orders;
import com.OrderManagement.module.address.Addresses;

import lombok.Data;

@Data
public class OrderDto implements Serializable {

	private LocalDateTime orderDate;
	private Boolean prepaid;
	private Double total_order_amount;
	private Integer quantity;
	private String productName;
	private String Brand;
	private String categoryName;

	private Map<String, String> shippingAddress = new HashMap<>();

	public static OrderDto getDtoOrder(Orders o) {
		OrderDto orderDto = new OrderDto();
		orderDto.setOrderDate(o.getOrderDate());
		orderDto.setPrepaid(o.getPrepaid());
		orderDto.setTotal_order_amount(o.getTotal_order_amount());
		orderDto.setQuantity(o.getOrderDetails().getQuantity());
		orderDto.setProductName(o.getOrderDetails().getProducts().getProductName());
		orderDto.setBrand(o.getOrderDetails().getProducts().getBrand());
		orderDto.setCategoryName(o.getOrderDetails().getProducts().getCategory().getCategoryName());

		Map<String, String> shippingAddress = new HashMap<>();
		Addresses addresses = o.getShippingAddress();
		if (addresses != null) {
			shippingAddress.put("name", addresses.getName());
			shippingAddress.put("house", addresses.getHouseNo());
			shippingAddress.put("contact", addresses.getContact());
			shippingAddress.put("city", addresses.getCity());
			shippingAddress.put("state", addresses.getState());
			shippingAddress.put("country", addresses.getCountry());
			shippingAddress.put("landmark", addresses.getLandmark());
			shippingAddress.put("postalCode", addresses.getPostalCode().getPINCODE().toString());
			orderDto.setShippingAddress(shippingAddress);
		}
		return orderDto;
	}

}
