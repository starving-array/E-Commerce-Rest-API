package com.OrderManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.OrderManagement.module.Products;

public interface ProductDao extends JpaRepository<Products, Integer> {

	@Query("select c from Products c where productName like %:name%")
	public List<Products> getProductByname(String name);

	public List<Products> findByOrderByProductAddedDateDesc();

	public List<Products> findByOrderByProductAddedDate();

	public List<Products> findByCategory_IdByOrderByProductAddedDateDesc(Integer category_Id);

	public List<Products> findByCategory_IdByOrderByProductAddedDate(Integer category_Id);

	public List<Products> findByCategory_IdByOrderByProductNameDesc(Integer category_Id);

	public List<Products> findByCategory_IdByOrderByProductName(Integer category_Id);

// price
	public List<Products> findByOrderByMarket_PriceDesc();

	public List<Products> findByOrderByMarket_Price();

	public List<Products> findByCategory_IdByOrderByMarket_PriceDesc(Integer category_Id);

	public List<Products> findByCategory_IdByOrderByMarket_Price(Integer category_Id);

// between
	public List<Products> findByMarket_PriceBetweenByOrderByMarket_PriceDesc(Double price, Double price2);

	public List<Products> findByMarket_PriceBetweenByOrderByMarket_Price(Double price, Double price2);

	public List<Products> findByCategory_IdAndMarket_PriceBetweenByOrderByMarket_PriceDesc(Integer category_Id,
			Double price, Double price2);

	public List<Products> findByCategory_IdAndMarket_PriceBetweenByOrderByMarket_Price(Integer category_Id,
			Double price, Double price2);

// min price
	public List<Products> findByMarket_PriceLessThanEqualByOrderByMarket_PriceDesc(Double price);

	public List<Products> findByMarket_PriceLessThanEqualByOrderByMarket_Price(Double price);

	public List<Products> findByCategory_IdAndMarket_PriceLessThanEqualByOrderByMarket_PriceDesc(Integer category_Id,
			Double price);

	public List<Products> findByCategory_IdAndMarket_PriceLessThanEqualByOrderByMarket_Price(Integer category_Id,
			Double price);

// max price
	public List<Products> findByMarket_PriceGreaterThanEqualByOrderByMarket_PriceDesc(Double price);

	public List<Products> findByMarket_PriceGreaterThanEqualByOrderByMarket_Price(Double price);

	public List<Products> findByCategory_IdAndMarket_PriceGreaterThanEqualByOrderByMarket_PriceDesc(Integer category_Id,
			Double price);

	public List<Products> findByCategory_IdAndMarket_PriceGreaterThanEqualByOrderByMarket_Price(Integer category_Id,
			Double price);

// feedback

	public List<Products> findByOrderByProductFeedBackDesc();

	public List<Products> findByOrderByProductFeedBack();
}
