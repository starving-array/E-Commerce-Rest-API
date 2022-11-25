package com.OrderManagement.Admin.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrderManagement.Admin.module.AdminCredential;
import com.OrderManagement.Admin.module.CardCredential;
import com.OrderManagement.Admin.repository.AdminDao;
import com.OrderManagement.Admin.repository.CardCredentialDao;
import com.OrderManagement.DTO.OrderDto;
import com.OrderManagement.exceptions.AdminExpectation;
import com.OrderManagement.exceptions.OtherException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.Catagory;
import com.OrderManagement.module.CurrentSession;
import com.OrderManagement.module.Orders;
import com.OrderManagement.module.Payment;
import com.OrderManagement.module.Products;
import com.OrderManagement.module.User;
import com.OrderManagement.module.address.PostalCodes;
import com.OrderManagement.repository.CatagoryDao;
import com.OrderManagement.repository.OrderDao;
import com.OrderManagement.repository.PaymentDao;
import com.OrderManagement.repository.PostalDao;
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

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private PostalDao postalDao;

	@Autowired
	private PaymentDao paymentDao;

	@Override
	public List<User> getAllUser(String sessionId) throws UserException, AdminExpectation {
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
	public List<OrderDto> viewOrdersPaidByCardNo(String sessionId, Long cardNumber) throws AdminExpectation {
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

		List<OrderDto> listDtoOrder = new ArrayList<>();
		for (Orders o : orders) {
			listDtoOrder.add(new OrderDto().getDtoOrder(o));
		}
		return listDtoOrder;
	}

	@Override
	public List<OrderDto> viwOrderShipToPinCode(String sessionId, Integer pincode) throws AdminExpectation {
		List<Orders> orders = adminDao.viwOrderShipToPinCode(pincode);
		if (orders.isEmpty()) {
			throw new AdminExpectation("No orders found that sent to this PIN " + pincode);
		}
		List<OrderDto> listDtoOrder = new ArrayList<>();
		for (Orders o : orders) {
			listDtoOrder.add(new OrderDto().getDtoOrder(o));
		}
		return listDtoOrder;
	}

	@Override
	public List<OrderDto> viewOrdersByCity(String sessionId, String city) throws AdminExpectation {
		List<Orders> orders = adminDao.viwOrderByCity(city);
		if (orders.isEmpty()) {
			throw new AdminExpectation("No orders found that sent to this city " + city);
		}
		List<OrderDto> listDtoOrder = new ArrayList<>();
		for (Orders o : orders) {
			listDtoOrder.add(new OrderDto().getDtoOrder(o));
		}
		return listDtoOrder;
	}

	@Override
	public List<OrderDto> viewOrderByState(String sessionId, String state) throws AdminExpectation {
		List<Orders> orders = adminDao.viwOrderByState(state);
		if (orders.isEmpty()) {
			throw new AdminExpectation("No orders found that sent to this state " + state);
		}
		List<OrderDto> listDtoOrder = new ArrayList<>();
		for (Orders o : orders) {
			listDtoOrder.add(new OrderDto().getDtoOrder(o));
		}
		return listDtoOrder;
	}

	@Override
	public List<OrderDto> viewOrderByCustomer(String sessionId, Integer customerId)
			throws AdminExpectation, UserException {
		List<Orders> orders = adminDao.viwOrderByUser(customerId);
		if (orders.isEmpty()) {
			throw new AdminExpectation(customerId + "User hasn't ordered anything yet");
		}
		List<OrderDto> listDtoOrder = new ArrayList<>();
		for (Orders o : orders) {
			listDtoOrder.add(new OrderDto().getDtoOrder(o));
		}
		return listDtoOrder;
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
		products.setProductAddedDate(LocalDateTime.now());
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

	@Override
	public List<Catagory> getAllCategory() throws OtherException {
		List<Catagory> catagories = catagoryDao.findAll();
		if (catagories.size() == 0) {
			throw new OtherException("No category found");
		}
		return catagories;
	}

	@Override
	public List<PostalCodes> addPostalCodes(String sessionId, List<PostalCodes> postalCodes) throws AdminExpectation {
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
		List<PostalCodes> listOfCodes = new ArrayList<>();
		for (PostalCodes p : postalCodes) {
			PostalCodes postalCode = postalDao.getByCode(p.getPINCODE());
			if (postalCode != null) {
				if (!postalCode.isActive()) {
					postalCode.setActive(true);
					postalCode = postalDao.save(postalCode);
					listOfCodes.add(postalCode);
				}
				continue;
			}
			postalCode = new PostalCodes();
			postalCode.setActive(true);
			postalCode.setPINCODE(p.getPINCODE());
			postalCode = postalDao.save(postalCode);
			listOfCodes.add(postalCode);
		}
		return listOfCodes;
	}

	@Override
	public List<PostalCodes> deactivatePostalCodes(String sessionId, List<PostalCodes> postalCodes)
			throws AdminExpectation {
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
		List<PostalCodes> listOfCodes = new ArrayList<>();
		for (PostalCodes p : postalCodes) {
			PostalCodes postalCode = postalDao.getByCode(p.getPINCODE());
			if (postalCode == null) {
				continue;
			}
			postalCode.setActive(false);
			postalCode = postalDao.save(postalCode);
			listOfCodes.add(postalCode);
		}
		return listOfCodes;
	}

	@Override
	public List<PostalCodes> reactivePostalCodes(String sessionId, List<PostalCodes> postalCodes)
			throws AdminExpectation {
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
		List<PostalCodes> listOfCodes = new ArrayList<>();
		for (PostalCodes p : postalCodes) {
			PostalCodes postalCode = postalDao.getByCode(p.getPINCODE());
			if (postalCode == null) {
				continue;
			}
			postalCode.setActive(true);
			postalCode = postalDao.save(postalCode);
			listOfCodes.add(postalCode);
		}
		return listOfCodes;
	}

	@Override
	public Payment addPaymentMethod(String sessionId, String methodname) throws AdminExpectation, OtherException {
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
		Payment payment = paymentDao.findByMethodName(methodname);
		if (payment == null) {
			payment = new Payment();
			payment.setActive(true);
			payment.setMethodName(methodname);
			payment = paymentDao.save(payment);
		}

		return payment;
	}

	@Override
	public Payment reactivePaymentMethod(String sessionId, String methodname) throws AdminExpectation, OtherException {
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
		Payment payment = paymentDao.findByMethodName(methodname);
		if (payment != null) {
			payment.setActive(true);
			payment = paymentDao.save(payment);
			return payment;
		} else {
			throw new OtherException("Method not exists");
		}

	}

	@Override
	public Payment deactivePaymentMethod(String sessionId, String methodname) throws AdminExpectation, OtherException {
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
		Payment payment = paymentDao.findByMethodName(methodname);
		if (payment != null) {
			payment.setActive(false);
			payment = paymentDao.save(payment);
			return payment;
		} else {
			throw new OtherException("Method not exists");
		}
	}

}
