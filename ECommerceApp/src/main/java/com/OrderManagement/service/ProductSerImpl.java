package com.OrderManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrderManagement.exceptions.OtherException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.module.Products;
import com.OrderManagement.repository.ProductDao;
@Service
public class ProductSerImpl implements ProductService{
	
	@Autowired
	private ProductDao productDao;
	

	@Override
	public List<Products> getProductByName(String name) throws ProductException {
		List<Products> list = productDao.getProductByname(name);
		if(list.size()==0) {
			throw new ProductException("No product exists");
		}
		return list;
	}

	@Override
	public Products getProductById(Integer id) throws ProductException {
		Optional<Products> prOptional = productDao.findById(id);
		if(prOptional.isEmpty()) {
			throw new ProductException("No product exists");
		}
		return prOptional.get();
	}

	@Override
	public List<Products> getProductByDefaultByDateDesc() throws ProductException, OtherException {
		List<Products> list = productDao.findByOrderByProductAddedDateDesc();
		if(list.size()==0) {
			throw new ProductException("No product found");
		}
		
		
		return null;
	}

	@Override
	public List<Products> getProductByDefaultByDate() throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductDefaultByCategorySortByDateDesc(Integer categoryId)
			throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductByCategorySortByDate(Integer categoryId) throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsByCategorySortByName(Integer categoryId) throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsByCategorySortByNameDesc(Integer categoryId)
			throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsSortByPriceDesc() throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsSortByPriceAsc() throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsSortByPriceByCategoryDesc(Integer categoryId)
			throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsSortByPriceByCategory(Integer categoryId) throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsBetweenPriceSortByPriceDesc(Double max, Double min)
			throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsBetweenPriceSortByPriceAsc(Double max, Double min)
			throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsBetweenPriceSortByPriceByCategoryDesc(Integer categoryId, Double max, Double min)
			throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsBetweenPriceSortByPriceByCategory(Integer categoryId, Double max, Double min)
			throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsMinPriceSortByPriceDesc(Double min) throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsMinPriceSortByPriceAsc(Double min) throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsMinPriceSortByPriceByCategoryDesc(Integer categoryId, Double min)
			throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsMinPriceSortByPriceByCategory(Integer categoryId, Double min)
			throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsMaxPriceSortByPriceDesc(Double max) throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsMaxPriceSortByPriceAsc(Double max) throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsMaxPriceSortByPriceByCategoryDesc(Integer categoryId, Double max)
			throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductsMaxPriceSortByPriceByCategory(Integer categoryId, Double max)
			throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductSortByFeedback() throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> getProductSortByFeedbackDesc() throws ProductException, OtherException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
