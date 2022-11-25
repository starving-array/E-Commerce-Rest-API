package com.OrderManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrderManagement.DTO.CartDto;
import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.CartDetails;
import com.OrderManagement.module.CurrentSession;
import com.OrderManagement.module.Products;
import com.OrderManagement.module.User;
import com.OrderManagement.module.Usercart;
import com.OrderManagement.repository.CartDao;
import com.OrderManagement.repository.CartDetailsDao;
import com.OrderManagement.repository.ProductDao;
import com.OrderManagement.repository.SessionDao;
import com.OrderManagement.repository.UserDao;

@Service
public class AddToCartImpl implements AddCartService {

	@Autowired
	private ProductDao pdao;

	@Autowired
	private SessionDao sessionDao;

	@Autowired
	private UserDao udao;

	@Autowired
	private CartDao cartDao;

	@Autowired
	private CartDetailsDao cartDetailsDao;

	@Override
	public CartDto addToCartProducts(Integer productId, String sessionId, Integer userid,Integer quantity)
			throws UserException, ProductException, LoginException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		
		if (cur.getUserId() != userid) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> user = udao.findById(userid);
		User userCurrent = user.get();

		Optional<Products> postopt = pdao.findById(productId);
		if (postopt.isEmpty()) {
			throw new ProductException("Product doesn't exist");
		}

		Products products = postopt.get();

		Usercart usercart = userCurrent.getCart();
		
		// check to see if the product already
		// in the cart, then increment the quantity
		
		CartDetails cartDetails = cartDao.getByProduct(productId);
		if(cartDetails == null) {
			cartDetails = new CartDetails();
			cartDetails.setProductCart(products);
			cartDetails.setQuantity(quantity);
			cartDetails.setUsercart(usercart);
			cartDetails.setProductAddDate(LocalDateTime.now());
			usercart.getCartDetails().add(cartDetails);
		}else {
			cartDetails.setModifyDate(LocalDateTime.now());
			cartDetails.setQuantity(cartDetails.getQuantity()+quantity);
		}
		cartDao.save(usercart);
//		udao.save(userCurrent);
		
		//cartdto
		CartDto cartDto = new CartDto();
		cartDto.setProductName(products.getProductName());
		cartDto.setProductPrice(products.getSaleprice()*quantity);
		cartDto.setQuantity(quantity);
		
		return cartDto;
	}

	@Override
	public List<CartDto> viewCart(String sessionId,Integer userid) throws ProductException, LoginException, UserException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		//
		if (cur.getUserId() != userid) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> user = udao.findById(userid);
		User userCurrent = user.get();

		List<CartDetails> listCartDetails = userCurrent.getCart().getCartDetails();// userCurrent.get
		List<CartDto> dtoList = new ArrayList<>();
		for (CartDetails i : listCartDetails) {
			Products products = i.getProductCart();
			CartDto cart = new CartDto();
			cart.setQuantity(i.getQuantity());
			cart.setProductName(products.getProductName());
			cart.setProductPrice(products.getSaleprice());
			// pending dto info can be add here

			// ---------------
			dtoList.add(cart);
		}
		return dtoList;
	}

	@Override
	public CartDto modifyCart(CartDetails cartDetails, String sessionId, Integer userid)
			throws ProductException, LoginException, UserException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		//
		if (cur.getUserId() != userid) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> user = udao.findById(userid);
		User userCurrent = user.get();


		Optional<CartDetails> cartOptional = cartDetailsDao.findById(cartDetails.getCartDetailId());
		if (cartOptional.isEmpty()) {
			throw new UserException("This product doesn't belong to this account");
		}
		// for now lets assume the stock quantity is infinitive for a product
		// so we wont check if the modified quantity will be invalid
		// or not
		CartDetails existCartDetails = cartOptional.get();
		existCartDetails.setQuantity(cartDetails.getQuantity());
		existCartDetails.setModifyDate(LocalDateTime.now());
		cartDetailsDao.save(existCartDetails);
		CartDto cartDto = new CartDto();
		cartDto.setProductName(existCartDetails.getProductCart().getProductName());
		cartDto.setProductPrice(existCartDetails.getProductCart().getSaleprice()*cartDetails.getQuantity());
		cartDto.setQuantity(cartDetails.getQuantity());
		
		return cartDto;
	}

	@Override
	public CartDto deleteFromCart(Integer cartId, String sessionId,Integer userid)
			throws ProductException, LoginException, UserException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}
		//
		if (cur.getUserId() != userid) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> user = udao.findById(userid);
		User userCurrent = user.get();


		Optional<CartDetails> cartOptional = cartDetailsDao.findById(cartId);
		if (cartOptional.isEmpty()) {
			throw new UserException("This product doesn't belong to this account");
		}
		CartDetails exCartDetails = cartOptional.get();
		CartDto cartDto = new CartDto();
		cartDto.setProductName(exCartDetails.getProductCart().getProductName());
		cartDto.setProductPrice(exCartDetails.getProductCart().getSaleprice()*exCartDetails.getQuantity());
		cartDto.setQuantity(exCartDetails.getQuantity());
		
		cartDetailsDao.delete(exCartDetails);
		return cartDto;
	}

}
