package com.OrderManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.DTO.ProductDto;
import com.OrderManagement.exceptions.OtherException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@CrossOrigin
	@GetMapping("/name:/{name}")
	public ResponseEntity<List<ProductDto>> getProductsByName(@PathVariable("name") String name)
			throws ProductException {
		List<ProductDto> list = productService.getProductByName(name);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/id:/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Integer id) throws ProductException {
		ProductDto products = productService.getProductById(id);
		return new ResponseEntity<ProductDto>(products, HttpStatus.OK);
	}

	// sorting

	@CrossOrigin
	@GetMapping("/sortby/date=desc")
	public ResponseEntity<List<ProductDto>> sortByDateDesc() throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductByDefaultByDateDesc();
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/date=asc")
	public ResponseEntity<List<ProductDto>> sortByDateAsc() throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductByDefaultByDate();
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/{categoryid}/date=desc")
	public ResponseEntity<List<ProductDto>> categorySortByDateDesc(@PathVariable("categoryid") Integer categoryId)
			throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductDefaultByCategorySortByDateDesc(categoryId);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/{categoryid}/date=asc")
	public ResponseEntity<List<ProductDto>> categorySortByDateAsc(@PathVariable("categoryid") Integer categoryId)
			throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductByCategorySortByDate(categoryId);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

//
//	// name
	@CrossOrigin
	@GetMapping("/sortby/{categoryid}/name=desc")
	public ResponseEntity<List<ProductDto>> categorySortByNameDesc(@PathVariable("categoryid") Integer categoryId)
			throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsByCategorySortByNameDesc(categoryId);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/{categoryid}/name=asc")
	public ResponseEntity<List<ProductDto>> categorySortByNameAsc(@PathVariable("categoryid") Integer categoryId)
			throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsByCategorySortByName(categoryId);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

//
//	// price
	@CrossOrigin
	@GetMapping("/sortby/price=desc")
	public ResponseEntity<List<ProductDto>> sortByPriceDesc() throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsSortByPriceDesc();
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/price=asc")
	public ResponseEntity<List<ProductDto>> sortByPriceAsc() throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsSortByPriceAsc();
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/{categoryid}/price=desc")
	public ResponseEntity<List<ProductDto>> categorySortByPriceDesc(@PathVariable("categoryid") Integer categoryId)
			throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsSortByPriceByCategoryDesc(categoryId);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/{categoryid}/price=asc")
	public ResponseEntity<List<ProductDto>> categorySortByPriceAsc(@PathVariable("categoryid") Integer categoryId)
			throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsSortByPriceByCategory(categoryId);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

//
//	// between
	@CrossOrigin
	@GetMapping("/sortby/maxprice/{max}/minprice/{min}/price=desc")
	public ResponseEntity<List<ProductDto>> sortByMaxMinPriceDesc(@PathVariable("max") Double max,
			@PathVariable("min") Double min) throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsBetweenPriceSortByPriceDesc(max, min);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/maxprice/{max}/minprice/{min}/price=asc")
	public ResponseEntity<List<ProductDto>> sortByMaxMinPriceAsc(@PathVariable("max") Double max,
			@PathVariable("min") Double min) throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsBetweenPriceSortByPriceAsc(max, min);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/{categoryid}/maxprice/{max}/minprice/{min}/price=desc")
	public ResponseEntity<List<ProductDto>> CategorySortByMaxMinPriceDesc(@PathVariable("categoryid") Integer categoryId,
			@PathVariable("max") Double max, @PathVariable("min") Double min) throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsBetweenPriceSortByPriceByCategoryDesc(categoryId, max, min);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/{categoryid}/maxprice/{max}/minprice/{min}/price=asc")
	public ResponseEntity<List<ProductDto>> CategorySortByMaxMinPriceAsc(@PathVariable("categoryid") Integer categoryId,
			@PathVariable("max") Double max, @PathVariable("min") Double min) throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsBetweenPriceSortByPriceByCategory(categoryId, max, min);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

//
//	// min
//
	@CrossOrigin
	@GetMapping("/sortby/minprice/{min}/price=desc")
	public ResponseEntity<List<ProductDto>> sortByMinPriceDesc(@PathVariable("min") Double min)
			throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsMinPriceSortByPriceDesc(min);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/minprice/{min}/price=asc")
	public ResponseEntity<List<ProductDto>> sortByMinPriceAsc(@PathVariable("min") Double min)
			throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsMinPriceSortByPriceAsc(min);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/{categoryid}/minprice/{min}/price=desc")
	public ResponseEntity<List<ProductDto>> CategorySortByMinPriceDesc(@PathVariable("categoryid") Integer categoryId,
			@PathVariable("min") Double min) throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsMinPriceSortByPriceByCategoryDesc(categoryId, min);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/{categoryid}/minprice/{min}/price=asc")
	public ResponseEntity<List<ProductDto>> CategorySortByMinPriceAsc(@PathVariable("categoryid") Integer categoryId,
			@PathVariable("min") Double min) throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsMinPriceSortByPriceByCategory(categoryId, min);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

//
//	// max
	@CrossOrigin
	@GetMapping("/sortby/maxprice/{max}/price=desc")
	public ResponseEntity<List<ProductDto>> sortByMaxPriceDesc(@PathVariable("max") Double max)
			throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsMaxPriceSortByPriceDesc(max);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/maxprice/{max}/price=asc")
	public ResponseEntity<List<ProductDto>> sortByMaxPriceAsc(@PathVariable("max") Double max)
			throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsMaxPriceSortByPriceAsc(max);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/{categoryid}/maxprice/{max}/price=desc")
	public ResponseEntity<List<ProductDto>> CategorySortByMaxPriceDesc(@PathVariable("categoryid") Integer categoryId,
			@PathVariable("max") Double max) throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsMaxPriceSortByPriceByCategoryDesc(categoryId, max);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/{categoryid}/maxprice/{max}/price=asc")
	public ResponseEntity<List<ProductDto>> CategorySortByMaxPriceAsc(@PathVariable("categoryid") Integer categoryId,
			@PathVariable("max") Double max) throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductsMaxPriceSortByPriceByCategory(categoryId, max);
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

//
//	// feedback
//
	@CrossOrigin
	@GetMapping("/sortby/feedback=desc")
	public ResponseEntity<List<ProductDto>> sortByFeedbackDesc() throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductSortByProductFeedBackDesc();
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/feedback=asc")
	public ResponseEntity<List<ProductDto>> sortByFeedbackAsc() throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductSortByProductFeedBack();
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}
	// rating

	@CrossOrigin
	@GetMapping("/sortby/rating=desc")
	public ResponseEntity<List<ProductDto>> sortByRatingDesc() throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductSortByProductRatingDesc();
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/sortby/rating=asc")
	public ResponseEntity<List<ProductDto>> sortByRatingAsc() throws ProductException, OtherException {
		List<ProductDto> list = productService.getProductSortByProductRating();
		return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
	}

}
