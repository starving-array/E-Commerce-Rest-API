package com.OrderManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrderManagement.DTO.ProductDto;
import com.OrderManagement.exceptions.OtherException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.module.Products;
import com.OrderManagement.repository.ProductDao;

@Service
public class ProductSerImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	public List<ProductDto> getDtoFormatOfProduct(List<Products> list) {
		List<ProductDto> listOfProducts = new ArrayList<>();
		for (Products p : list) {
			ProductDto productDto = new ProductDto();
			productDto.setProductId(p.getProductId());
			productDto.setProductName(p.getProductName());
			productDto.setBrand(p.getBrand());
			productDto.setCategory(p.getCategory().getCategoryName());
			productDto.setPrice(p.getSaleprice());
			productDto.setLaunchDate(p.getProductAddedDate());
			productDto.setProductrating(p.getProductRating());
			listOfProducts.add(productDto);
		}
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductByName(String name) throws ProductException {
		List<Products> list = productDao.getProductByname(name);
		if (list.size() == 0) {
			throw new ProductException("No product exists");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public ProductDto getProductById(Integer id) throws ProductException {
		Optional<Products> prOptional = productDao.findById(id);
		if (prOptional.isEmpty()) {
			throw new ProductException("No product exists");
		}
		Products p = prOptional.get();

		ProductDto productDto = new ProductDto();
		productDto.setProductId(p.getProductId());
		productDto.setProductName(p.getProductName());
		productDto.setBrand(p.getBrand());
		productDto.setCategory(p.getCategory().getCategoryName());
		productDto.setPrice(p.getSaleprice());
		productDto.setLaunchDate(p.getProductAddedDate());
		productDto.setProductrating(p.getProductRating());
		return productDto;
	}

	@Override
	public List<ProductDto> getProductByDefaultByDateDesc() throws ProductException, OtherException {
		List<Products> list = productDao.findByOrderByProductAddedDateDesc();
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductByDefaultByDate() throws ProductException, OtherException {
		List<Products> list = productDao.findByOrderByProductAddedDate();
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductDefaultByCategorySortByDateDesc(Integer categoryId)
			throws ProductException, OtherException {
		List<Products> list = productDao.findByCategory_IdOrderByProductAddedDateDesc(categoryId);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductByCategorySortByDate(Integer categoryId) throws ProductException, OtherException {
		List<Products> list = productDao.findByCategory_IdOrderByProductAddedDate(categoryId);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsByCategorySortByName(Integer categoryId)
			throws ProductException, OtherException {
		List<Products> list = productDao.findByCategory_IdOrderByProductName(categoryId);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsByCategorySortByNameDesc(Integer categoryId)
			throws ProductException, OtherException {
		List<Products> list = productDao.findByCategory_IdOrderByProductNameDesc(categoryId);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsSortByPriceDesc() throws ProductException, OtherException {
		List<Products> list = productDao.findByOrderBySalepriceDesc();
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsSortByPriceAsc() throws ProductException, OtherException {
		List<Products> list = productDao.findByOrderBySaleprice();
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsSortByPriceByCategoryDesc(Integer categoryId)
			throws ProductException, OtherException {
		List<Products> list = productDao.findByCategory_IdOrderBySalepriceDesc(categoryId);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsSortByPriceByCategory(Integer categoryId)
			throws ProductException, OtherException {
		List<Products> list = productDao.findByCategory_IdOrderBySaleprice(categoryId);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

// between
	@Override
	public List<ProductDto> getProductsBetweenPriceSortByPriceDesc(Double max, Double min)
			throws ProductException, OtherException {
		List<Products> list = productDao.findBySalepriceBetweenOrderBySalepriceDesc(max, min);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsBetweenPriceSortByPriceAsc(Double max, Double min)
			throws ProductException, OtherException {
		List<Products> list = productDao.findBySalepriceBetweenOrderBySaleprice(max, min);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsBetweenPriceSortByPriceByCategoryDesc(Integer categoryId, Double max, Double min)
			throws ProductException, OtherException {
		List<Products> list = productDao.findByCategory_IdAndSalepriceBetweenOrderBySalepriceDesc(categoryId, max, min);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsBetweenPriceSortByPriceByCategory(Integer categoryId, Double max, Double min)
			throws ProductException, OtherException {
		List<Products> list = productDao.findByCategory_IdAndSalepriceBetweenOrderBySaleprice(categoryId, max, min);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

// min
	@Override
	public List<ProductDto> getProductsMinPriceSortByPriceDesc(Double min) throws ProductException, OtherException {
		List<Products> list = productDao.findBySalepriceLessThanEqualOrderBySalepriceDesc(min);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsMinPriceSortByPriceAsc(Double min) throws ProductException, OtherException {
		List<Products> list = productDao.findBySalepriceLessThanEqualOrderBySaleprice(min);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsMinPriceSortByPriceByCategoryDesc(Integer categoryId, Double min)
			throws ProductException, OtherException {
		List<Products> list = productDao.findByCategory_IdAndSalepriceLessThanEqualOrderBySalepriceDesc(categoryId,
				min);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsMinPriceSortByPriceByCategory(Integer categoryId, Double min)
			throws ProductException, OtherException {
		List<Products> list = productDao.findByCategory_IdAndSalepriceLessThanEqualOrderBySaleprice(categoryId, min);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

//max
	@Override
	public List<ProductDto> getProductsMaxPriceSortByPriceDesc(Double max) throws ProductException, OtherException {
		List<Products> list = productDao.findBySalepriceGreaterThanEqualOrderBySalepriceDesc(max);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsMaxPriceSortByPriceAsc(Double max) throws ProductException, OtherException {
		List<Products> list = productDao.findBySalepriceGreaterThanEqualOrderBySaleprice(max);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsMaxPriceSortByPriceByCategoryDesc(Integer categoryId, Double max)
			throws ProductException, OtherException {
		List<Products> list = productDao.findByCategory_IdAndSalepriceGreaterThanEqualOrderBySalepriceDesc(categoryId,
				max);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductsMaxPriceSortByPriceByCategory(Integer categoryId, Double max)
			throws ProductException, OtherException {
		List<Products> list = productDao.findByCategory_IdAndSalepriceGreaterThanEqualOrderBySaleprice(categoryId,
				max);
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductSortByProductFeedBackDesc() throws ProductException, OtherException {
		List<Products> list = productDao.findByOrderByProductFeedBackDesc();
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductSortByProductFeedBack() throws ProductException, OtherException {
		List<Products> list = productDao.findByOrderByProductFeedBack();
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductSortByProductRatingDesc() throws ProductException, OtherException {
		List<Products> list = productDao.findByOrderByProductRatingDesc();
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}

	@Override
	public List<ProductDto> getProductSortByProductRating() throws ProductException, OtherException {
		List<Products> list = productDao.findByOrderByProductRating();
		if (list.size() == 0) {
			throw new ProductException("No product found");
		}

		List<ProductDto> listOfProducts = getDtoFormatOfProduct(list);
		return listOfProducts;
	}


}
