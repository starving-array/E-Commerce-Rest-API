package com.OrderManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.module.Products;
import com.OrderManagement.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/name:/{name}")
	public ResponseEntity<List<Products>> getProductsByName(@PathVariable("name") String name) throws ProductException {
		List<Products> list = productService.getProductByName(name);
		return new ResponseEntity<List<Products>>(list, HttpStatus.OK);
	}

	@GetMapping("/id:/{id}")
	public ResponseEntity<Products> getProductById(@PathVariable("id") Integer id) throws ProductException {
		Products products = productService.getProductById(id);
		return new ResponseEntity<Products>(products, HttpStatus.OK);
	}
	
	

}
