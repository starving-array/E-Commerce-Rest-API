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

	public List<Products> findByCategory_IdOrderByProductAddedDateDesc(Integer category_Id);

	public List<Products> findByCategory_IdOrderByProductAddedDate(Integer category_Id);

	public List<Products> findByCategory_IdOrderByProductNameDesc(Integer category_Id);

	public List<Products> findByCategory_IdOrderByProductName(Integer category_Id);

// price
	public List<Products> findByOrderBySalepriceDesc();

	public List<Products> findByOrderBySaleprice();

	public List<Products> findByCategory_IdOrderBySalepriceDesc(Integer category_Id);

	public List<Products> findByCategory_IdOrderBySaleprice(Integer category_Id);

// between
	public List<Products> findBySalepriceBetweenOrderBySalepriceDesc(Double price, Double price2);

	public List<Products> findBySalepriceBetweenOrderBySaleprice(Double price, Double price2);

	public List<Products> findByCategory_IdAndSalepriceBetweenOrderBySalepriceDesc(Integer category_Id, Double price,
			Double price2);

	public List<Products> findByCategory_IdAndSalepriceBetweenOrderBySaleprice(Integer category_Id, Double price,
			Double price2);

// min price
	public List<Products> findBySalepriceLessThanEqualOrderBySalepriceDesc(Double price);

	public List<Products> findBySalepriceLessThanEqualOrderBySaleprice(Double price);

	public List<Products> findByCategory_IdAndSalepriceLessThanEqualOrderBySalepriceDesc(Integer category_Id,
			Double price);

	public List<Products> findByCategory_IdAndSalepriceLessThanEqualOrderBySaleprice(Integer category_Id, Double price);

// max price
	public List<Products> findBySalepriceGreaterThanEqualOrderBySalepriceDesc(Double price);

	public List<Products> findBySalepriceGreaterThanEqualOrderBySaleprice(Double price);

	public List<Products> findByCategory_IdAndSalepriceGreaterThanEqualOrderBySalepriceDesc(Integer category_Id,
			Double price);

	public List<Products> findByCategory_IdAndSalepriceGreaterThanEqualOrderBySaleprice(Integer category_Id,
			Double price);

// feedback

	public List<Products> findByOrderByProductFeedBackDesc();

	public List<Products> findByOrderByProductFeedBack();

	// rating

	public List<Products> findByOrderByProductRatingDesc();

	public List<Products> findByOrderByProductRating();
}
