package com.OrderManagement.module;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer productId;
	private String productName;
	private String Brand;
	private Double sale_Price;
	private Double market_Price;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "category_Id", referencedColumnName = "id")
	private Catagory category;
	
	@JsonIgnore
	@OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
	private List<OrderDetails> orders;
	
	@JsonIgnore
	@OneToMany(mappedBy = "productCart", cascade = CascadeType.ALL)
	private List<CartDetails> carts;
	
	@JsonIgnore
	@OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
	private List<Feedback> feedbacks;
}
