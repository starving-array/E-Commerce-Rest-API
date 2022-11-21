package com.OrderManagement.service;

import java.util.List;

import com.OrderManagement.exceptions.OtherException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.module.Products;

public interface ProductService {

	public List<Products> getProductByName(String name) throws ProductException;

	public Products getProductById(Integer id) throws ProductException;

	// sort by date
	// by default desc=> recent date=> new added product
	// for all product
	public List<Products> getProductByDefaultByDateDesc() throws ProductException, OtherException;

	public List<Products> getProductByDefaultByDate() throws ProductException, OtherException;

	// category with date
	public List<Products> getProductDefaultByCategorySortByDateDesc(Integer categoryId)
			throws ProductException, OtherException;

	public List<Products> getProductByCategorySortByDate(Integer categoryId) throws ProductException, OtherException;

	// by name and cat public List<Catagory>
	// findByCategoryNameByOrderByCategoryName();
	public List<Products> getProductsByCategorySortByName(Integer categoryId) throws ProductException, OtherException;

	public List<Products> getProductsByCategorySortByNameDesc(Integer categoryId)
			throws ProductException, OtherException;

	// sort by price
	public List<Products> getProductsSortByPriceDesc() throws ProductException, OtherException;

	public List<Products> getProductsSortByPriceAsc() throws ProductException, OtherException;

	public List<Products> getProductsSortByPriceByCategoryDesc(Integer categoryId)
			throws ProductException, OtherException;

	public List<Products> getProductsSortByPriceByCategory(Integer categoryId) throws ProductException, OtherException;

	// price between
	public List<Products> getProductsBetweenPriceSortByPriceDesc(Double max, Double min)
			throws ProductException, OtherException;

	public List<Products> getProductsBetweenPriceSortByPriceAsc(Double max, Double min)
			throws ProductException, OtherException;

	public List<Products> getProductsBetweenPriceSortByPriceByCategoryDesc(Integer categoryId, Double max, Double min)
			throws ProductException, OtherException;

	public List<Products> getProductsBetweenPriceSortByPriceByCategory(Integer categoryId, Double max, Double min)
			throws ProductException, OtherException;

	// minimum price
	public List<Products> getProductsMinPriceSortByPriceDesc(Double min) throws ProductException, OtherException;

	public List<Products> getProductsMinPriceSortByPriceAsc(Double min) throws ProductException, OtherException;

	public List<Products> getProductsMinPriceSortByPriceByCategoryDesc(Integer categoryId, Double min)
			throws ProductException, OtherException;

	public List<Products> getProductsMinPriceSortByPriceByCategory(Integer categoryId, Double min)
			throws ProductException, OtherException;

	// maximum price
	public List<Products> getProductsMaxPriceSortByPriceDesc(Double max) throws ProductException, OtherException;

	public List<Products> getProductsMaxPriceSortByPriceAsc(Double max) throws ProductException, OtherException;

	public List<Products> getProductsMaxPriceSortByPriceByCategoryDesc(Integer categoryId, Double max)
			throws ProductException, OtherException;

	public List<Products> getProductsMaxPriceSortByPriceByCategory(Integer categoryId, Double max)
			throws ProductException, OtherException;

	// sort by feedback
	public List<Products> getProductSortByFeedback() throws ProductException, OtherException;

	public List<Products> getProductSortByFeedbackDesc() throws ProductException, OtherException;

	// sort by rating++

}
