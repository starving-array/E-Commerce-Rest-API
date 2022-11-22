package com.OrderManagement.Admin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrderManagement.Admin.module.AdminCredential;
import com.OrderManagement.Admin.module.CardCredential;
import com.OrderManagement.Admin.repository.CardCredentialDao;
import com.OrderManagement.exceptions.AdminExpectation;
import com.OrderManagement.exceptions.OtherException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.Catagory;
import com.OrderManagement.module.CurrentSession;
import com.OrderManagement.module.Orders;
import com.OrderManagement.module.Products;
import com.OrderManagement.module.User;
import com.OrderManagement.repository.CatagoryDao;
import com.OrderManagement.repository.OrderDao;
import com.OrderManagement.repository.ProductDao;
import com.OrderManagement.repository.SessionDao;
import com.OrderManagement.repository.UserDao;

@Service
public class AdminServImpl implements AdminService {

	@Autowired
	private SessionDao sessionDao;

	@Autowired
	private CardCredentialDao cardCredentialDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CatagoryDao catagoryDao;
	@Autowired
	private UserDao udao;

	
	@Override
	public List<User> getAllUser(String sessionId) throws UserException,AdminExpectation {
		// admin valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new AdminExpectation("Please log in to post");
		}

		Integer userid = cur.getUserId();
		String password = new AdminCredential().getPassword();
		if (userid != 0) {
			throw new AdminExpectation("Access denied");
		}
		// access cleared

		List<User> users = udao.findAll();
		if (users.isEmpty()) {
			throw new UserException("No user in database");
		}
		return users;
	}

	@Override
	public CardCredential addCardCredentials(String sessionId, CardCredential cardCredential) throws AdminExpectation {
		// admin valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new AdminExpectation("Please log in to post");
		}

		Integer userid = cur.getUserId();
		String password = new AdminCredential().getPassword();
		if (userid != 0) {
			throw new AdminExpectation("Access denied");
		}
		// access cleared
		CardCredential checkCredential = cardCredentialDao.checkIfCardExist(cardCredential.getCardNumber());
		if (checkCredential != null) {
			throw new AdminExpectation("This card already registered");
		}

		CardCredential cardCredentialNew = new CardCredential();
		cardCredentialNew.setBalance(cardCredential.getBalance());
		cardCredentialNew.setCardNumber(cardCredential.getCardNumber());
		cardCredentialNew.setCardPin(cardCredential.getCardPin());

		return cardCredentialDao.save(cardCredentialNew);
	}

	@Override
	public CardCredential addBalance(String sessionId, Long cardNumber, Double balance) throws AdminExpectation {
		// admin valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new AdminExpectation("Please log in to post");
		}

		Integer userid = cur.getUserId();
		String password = new AdminCredential().getPassword();
		if (userid != 0) {
			throw new AdminExpectation("Access denied");
		}
		// access cleared
		CardCredential checkCredential = cardCredentialDao.checkIfCardExist(cardNumber);
		if (checkCredential == null) {
			throw new AdminExpectation("Invalid card details");
		}
		checkCredential.setBalance(checkCredential.getBalance() + balance);

		return cardCredentialDao.save(checkCredential);
	}

	@Override
	public List<Orders> viewOrdersPaidByCardNo(String sessionId, Long cardNumber) throws AdminExpectation {
		// admin valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new AdminExpectation("Please log in to post");
		}

		Integer userid = cur.getUserId();
		String password = new AdminCredential().getPassword();
		if (userid != 0) {
			throw new AdminExpectation("Access denied");
		}
		// access cleared

		List<Orders> orders = orderDao.getOrderByPaymentSource(cardNumber.toString());
		if (orders.size() == 0) {
			throw new AdminExpectation("No orders found");
		}
		return orders;
	}

	@Override
	public List<Orders> viwOrderShipToPinCode(String sessionId, Integer pincode) throws AdminExpectation {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Orders> viewOrderBuyFromByPinCode(String sessionId, Integer pincode) throws AdminExpectation {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Orders> viewOrdersByCity(String sessionId, String city) throws AdminExpectation {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Orders> viewOrderByState(String sessionId, String state) throws AdminExpectation {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Orders> viewOrderByCustomer(String sessionId, Integer customerId)
			throws AdminExpectation, UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Products registerProduct(String sessionId, Products products, Integer catagoryId)
			throws AdminExpectation, OtherException {
		// admin valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new AdminExpectation("Please log in to post");
		}

		Integer userid = cur.getUserId();
		String password = new AdminCredential().getPassword();
		if (userid != 0) {
			throw new AdminExpectation("Access denied");
		}
		// access cleared
		Optional<Catagory> cOptional = catagoryDao.findById(catagoryId);
		if (cOptional.isEmpty()) {
			throw new OtherException("Invalid catagory id");
		}
		Catagory catagory = cOptional.get();
		catagory.getProducts().add(products);
		products.setCategory(catagory);
		return productDao.save(products);
	}

	// catagory
	@Override
	public Catagory addCatagory(String sessionId, Catagory catagory) throws OtherException, AdminExpectation {
		// admin valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new AdminExpectation("Please log in to post");
		}

		Integer userid = cur.getUserId();
		String password = new AdminCredential().getPassword();
		if (userid != 0) {
			throw new AdminExpectation("Access denied");
		}
		// access cleared

		Catagory catagory2 = catagoryDao.findbyname(catagory.getCategoryName());
		if (catagory2 != null) {
			throw new OtherException("This catagory already exits");
		}
		// if not
		catagory2 = new Catagory();
		catagory2.setCategoryName(catagory.getCategoryName());

		return catagoryDao.save(catagory2);
	}

}
