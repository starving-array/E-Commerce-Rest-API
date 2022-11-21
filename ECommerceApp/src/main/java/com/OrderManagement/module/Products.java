package com.OrderManagement.module;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products {
	@JsonIgnore 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer productId;
	private String productName;
	private String Brand;
	private Double sale_Price;
	private Double market_Price;
	private LocalDateTime productAddedDate;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "category_Id", referencedColumnName = "id")
	private Catagory category;
	
	@JsonIgnore
	@OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
	private List<OrderDetails> orders = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "productCart", cascade = CascadeType.ALL)
	private List<CartDetails> carts = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
	private List<Feedback> feedbacks = new ArrayList<>();
	private Integer productFeedBack;
}
