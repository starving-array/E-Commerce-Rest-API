package com.OrderManagement.service;

import java.util.List;

import com.OrderManagement.DTO.ProductDto;
import com.OrderManagement.exceptions.OtherException;
import com.OrderManagement.exceptions.ProductException;

public interface ProductService {

	public List<ProductDto> getProductByName(String name) throws ProductException;

	public ProductDto getProductById(Integer id) throws ProductException;

	// sort by date
	// by default desc=> recent date=> new added product
	// for all product
	public List<ProductDto> getProductByDefaultByDateDesc() throws ProductException, OtherException;

	public List<ProductDto> getProductByDefaultByDate() throws ProductException, OtherException;

	// category with date
	public List<ProductDto> getProductDefaultByCategorySortByDateDesc(Integer categoryId)
			throws ProductException, OtherException;

	public List<ProductDto> getProductByCategorySortByDate(Integer categoryId) throws ProductException, OtherException;

	// by name and cat public List<Catagory>
	// findByCategoryNameByOrderByCategoryName();
	public List<ProductDto> getProductsByCategorySortByName(Integer categoryId) throws ProductException, OtherException;

	public List<ProductDto> getProductsByCategorySortByNameDesc(Integer categoryId)
			throws ProductException, OtherException;

	// sort by price
	public List<ProductDto> getProductsSortByPriceDesc() throws ProductException, OtherException;

	public List<ProductDto> getProductsSortByPriceAsc() throws ProductException, OtherException;

	public List<ProductDto> getProductsSortByPriceByCategoryDesc(Integer categoryId)
			throws ProductException, OtherException;

	public List<ProductDto> getProductsSortByPriceByCategory(Integer categoryId)
			throws ProductException, OtherException;

	// price between
	public List<ProductDto> getProductsBetweenPriceSortByPriceDesc(Double max, Double min)
			throws ProductException, OtherException;

	public List<ProductDto> getProductsBetweenPriceSortByPriceAsc(Double max, Double min)
			throws ProductException, OtherException;

	public List<ProductDto> getProductsBetweenPriceSortByPriceByCategoryDesc(Integer categoryId, Double max, Double min)
			throws ProductException, OtherException;

	public List<ProductDto> getProductsBetweenPriceSortByPriceByCategory(Integer categoryId, Double max, Double min)
			throws ProductException, OtherException;

	// minimum price
	public List<ProductDto> getProductsMinPriceSortByPriceDesc(Double min) throws ProductException, OtherException;

	public List<ProductDto> getProductsMinPriceSortByPriceAsc(Double min) throws ProductException, OtherException;

	public List<ProductDto> getProductsMinPriceSortByPriceByCategoryDesc(Integer categoryId, Double min)
			throws ProductException, OtherException;

	public List<ProductDto> getProductsMinPriceSortByPriceByCategory(Integer categoryId, Double min)
			throws ProductException, OtherException;

	// maximum price
	public List<ProductDto> getProductsMaxPriceSortByPriceDesc(Double max) throws ProductException, OtherException;

	public List<ProductDto> getProductsMaxPriceSortByPriceAsc(Double max) throws ProductException, OtherException;

	public List<ProductDto> getProductsMaxPriceSortByPriceByCategoryDesc(Integer categoryId, Double max)
			throws ProductException, OtherException;

	public List<ProductDto> getProductsMaxPriceSortByPriceByCategory(Integer categoryId, Double max)
			throws ProductException, OtherException;

	// sort by feedback
	public List<ProductDto> getProductSortByProductFeedBackDesc() throws ProductException, OtherException;

	public List<ProductDto> getProductSortByProductFeedBack() throws ProductException, OtherException;


	// sort by rating++
	
	public List<ProductDto> getProductSortByProductRatingDesc() throws ProductException, OtherException;

	public List<ProductDto> getProductSortByProductRating() throws ProductException, OtherException;


}
