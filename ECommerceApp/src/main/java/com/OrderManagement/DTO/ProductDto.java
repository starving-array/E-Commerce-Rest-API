package com.OrderManagement.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductDto implements Serializable{
	
	private Integer productId;
	private String productName;
	private String Brand;
	private Double market_Price;
	private LocalDateTime productAddedDate;
	private Integer productFeedBack;
	

}
