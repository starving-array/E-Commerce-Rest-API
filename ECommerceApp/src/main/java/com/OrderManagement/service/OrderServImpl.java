package com.OrderManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrderManagement.Admin.module.CardCredential;
import com.OrderManagement.Admin.repository.CardCredentialDao;
import com.OrderManagement.exceptions.LoginException;
import com.OrderManagement.exceptions.OrderException;
import com.OrderManagement.exceptions.ProductException;
import com.OrderManagement.exceptions.UserException;
import com.OrderManagement.module.CartDetails;
import com.OrderManagement.module.CurrentSession;
import com.OrderManagement.module.OrderDetails;
import com.OrderManagement.module.Orders;
import com.OrderManagement.module.Payment;
import com.OrderManagement.module.PaymentSource;
import com.OrderManagement.module.Products;
import com.OrderManagement.module.User;
import com.OrderManagement.module.Usercart;
import com.OrderManagement.module.address.Addresses;
import com.OrderManagement.module.address.PostalCodes;
import com.OrderManagement.paymentMethods.CardFormat;
import com.OrderManagement.repository.AddressDao;
import com.OrderManagement.repository.CartDao;
import com.OrderManagement.repository.CartDetailsDao;
import com.OrderManagement.repository.OrderDao;
import com.OrderManagement.repository.PaymentDao;
import com.OrderManagement.repository.PaymentSourceDao;
import com.OrderManagement.repository.PostalDao;
import com.OrderManagement.repository.ProductDao;
import com.OrderManagement.repository.SessionDao;
import com.OrderManagement.repository.UserDao;

@Service
public class OrderServImpl implements OrderService {

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

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private CardCredentialDao cardCredentialDao;

	@Autowired
	private PaymentDao paymentDao;

	@Autowired
	private PaymentSourceDao paymentSourceDao;

	@Autowired
	private AddressDao addressDao;

	@Autowired
	private PostalDao postalDao;

	@Override
	public List<Orders> placeCartOrderPertial(List<Integer> cartIds, String sessionId, Integer userid,
			CardFormat cardFormat, Integer usercartId, Addresses billingAddress, Addresses shipAddresses,
			Integer billPincode, Integer shipPinCode) throws ProductException, LoginException, UserException {

		// user valid!
		// Check if the session id is valid
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

		// Address check
		// Address check
		// +++++++++++++++++++++++++++++++++++
		Addresses billingFromAddressToAppend = new Addresses();
		Addresses shipToAddressToAppend = new Addresses();

		// check if pincode serviceable for shipping
		PostalCodes shipCode = postalDao.getByCode(shipPinCode);
		if (shipCode == null || !shipCode.isActive()) {
			throw new ProductException("Product not deliverable to this localtion now");
		}

		// future upgrade analytic=> log this code as to see how many request came for a
		// perticular pincode
		shipToAddressToAppend.setName(shipAddresses.getName());
		shipToAddressToAppend.setContact(shipAddresses.getContact());
		shipToAddressToAppend.setHouseNo(shipAddresses.getHouseNo());
		shipToAddressToAppend.setCity(shipAddresses.getCity());
		shipToAddressToAppend.setState(shipAddresses.getState());
		shipToAddressToAppend.setCountry(shipAddresses.getCountry());
		shipToAddressToAppend.setLandmark(shipAddresses.getLandmark());

		if (shipCode == null) {
			shipCode = new PostalCodes();
			shipCode.setPINCODE(shipPinCode);
			shipCode.setActive(true);
			shipCode = postalDao.save(shipCode);
		}
		shipToAddressToAppend.setPostalCode(shipCode);
		shipToAddressToAppend.setUser(userCurrent);

		// billing from
		billingFromAddressToAppend.setName(billingAddress.getName());
		billingFromAddressToAppend.setContact(billingAddress.getContact());
		billingFromAddressToAppend.setHouseNo(billingAddress.getHouseNo());
		billingFromAddressToAppend.setCity(billingAddress.getCity());
		billingFromAddressToAppend.setState(billingAddress.getState());
		billingFromAddressToAppend.setCountry(billingAddress.getCountry());
		billingFromAddressToAppend.setLandmark(billingAddress.getLandmark());

		PostalCodes billCode = postalDao.getByCode(billPincode);
		if (billCode == null) {
			billCode = new PostalCodes();
			billCode.setPINCODE(billPincode);
			billCode.setActive(true);
			billCode = postalDao.save(billCode);
		}
		billingFromAddressToAppend.setPostalCode(billCode);
		billingFromAddressToAppend.setUser(userCurrent);
		// order will be assigned below and also flused down below at end

		// +++++++++++++++++++++++++++++++++++++
		// --------------------------

		// check if these cart belong to this user
		Optional<Usercart> userCartOptional = cartDao.findById(usercartId);
		if (userCartOptional.isEmpty()) {
			throw new UserException("Cart id invalid");
		}

		Usercart usercart = userCartOptional.get();
		if (userCurrent.getCart().getId() != usercart.getId()) {
			throw new UserException("This cart doesn't belongs to this id");
		}

		// card vaild!
		CardCredential cardCredential = cardCredentialDao.matchCard(cardFormat.getCardNo(), cardFormat.getPin());
		if (cardCredential == null) {
			throw new ProductException("Invalid card");
		}

		// payment method
		Payment payment = paymentDao.findByMethodName("card");
		if (payment == null || !payment.isActive()) {
			throw new ProductException("Payment method not supported now");
		}
		// source payment
		PaymentSource paymentSource = paymentSourceDao.findByAccountInfo(cardFormat.getCardNo().toString());
		if (paymentSource == null) {
			paymentSource = new PaymentSource();
			paymentSource.setAccountInfo(cardFormat.getCardNo().toString());
			paymentSource = paymentSourceDao.save(paymentSource);
		}

		long orderTotal = 0;

		List<Orders> ordersList = new ArrayList<>();

		for (Integer id : cartIds) {
			Optional<CartDetails> cartOptional = cartDetailsDao.findById(id);
			if (cartOptional.isEmpty()) {
				throw new ProductException("Wrong cart id");
			}
			CartDetails cartDetails = cartOptional.get();
			// format created for new order
			Orders newOrders = new Orders();
			OrderDetails newOrderDetails = new OrderDetails();
			Products products = cartDetails.getProductCart();
			int quantuty = cartDetails.getQuantity();

			// value assigned
			newOrderDetails.setQuantity(quantuty);
			newOrderDetails.setProducts(products);
			products.getOrders().add(newOrderDetails); // ## reverse
			products.getCarts().remove(cartDetails); // cart details removed

			newOrders.setOrderDate(LocalDateTime.now());
			newOrders.setTotal_order_amount(quantuty * products.getSaleprice());
			newOrders.setPrepaid(true);

			newOrders.setPayments(payment);
			payment.getOrders().add(newOrders); // ## reverse

			newOrders.setCustomer(userCurrent);
			userCurrent.getOrders().add(newOrders); // ## reverse

			newOrders.setOrderDetails(newOrderDetails);
			newOrderDetails.setOrders(newOrders); // order added to order details

			// address
			// billing
			newOrders.setBillingAddress(billingFromAddressToAppend);
			billingFromAddressToAppend.setOrders(newOrders); // ## reverse

			// shipping
			newOrders.setShippingAddress(shipToAddressToAppend);
			shipAddresses.setOrders(newOrders); // ## reverse

			newOrders.setSource(paymentSource);
			paymentSource.getOrders().add(newOrders); // ## reverse

			ordersList.add(newOrders);
			orderTotal += quantuty * products.getSaleprice();

		}
		if (orderTotal > cardCredential.getBalance()) {
			throw new ProductException("Insufficient balance");
		}
		cardCredential.setBalance(cardCredential.getBalance() - orderTotal);
		cardCredentialDao.save(cardCredential);

		for (Orders o : ordersList) {
			orderDao.save(o);
		}

		for (Integer id : cartIds) {
			Optional<CartDetails> cartOptional = cartDetailsDao.findById(id);
			CartDetails cartDetails = cartOptional.get();
			userCurrent.getCart().getCartDetails().remove(cartDetails);
			cartDetailsDao.delete(cartDetails);
		}

		return ordersList;
	}

	@Override
	public List<Orders> placeAllCartOrder(String sessionId, Integer userid, CardFormat cardFormat,
			Addresses billingAddress, Addresses shipAddresses, Integer billPincode, Integer shipPinCode)
			throws ProductException, LoginException, UserException {

		// user valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}

		if (cur.getUserId() != userid) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> user = udao.findById(userid);
		User userCurrent = user.get();

		// Address check
		// Address check
		// +++++++++++++++++++++++++++++++++++
		Addresses billingFromAddressToAppend = new Addresses();
		Addresses shipToAddressToAppend = new Addresses();

		// check if pincode serviceable for shipping
		PostalCodes shipCode = postalDao.getByCode(shipPinCode);
		if (shipCode == null || !shipCode.isActive()) {
			throw new ProductException("Product not deliverable to this localtion now");
		}

		// future upgrade analytic=> log this code as to see how many request came for a
		// perticular pincode
		shipToAddressToAppend.setName(shipAddresses.getName());
		shipToAddressToAppend.setContact(shipAddresses.getContact());
		shipToAddressToAppend.setHouseNo(shipAddresses.getHouseNo());
		shipToAddressToAppend.setCity(shipAddresses.getCity());
		shipToAddressToAppend.setState(shipAddresses.getState());
		shipToAddressToAppend.setCountry(shipAddresses.getCountry());
		shipToAddressToAppend.setLandmark(shipAddresses.getLandmark());

		if (shipCode == null) {
			shipCode = new PostalCodes();
			shipCode.setPINCODE(shipPinCode);
			shipCode.setActive(true);
			shipCode = postalDao.save(shipCode);
		}
		shipToAddressToAppend.setPostalCode(shipCode);
		shipToAddressToAppend.setUser(userCurrent);

		// billing from
		billingFromAddressToAppend.setName(billingAddress.getName());
		billingFromAddressToAppend.setContact(billingAddress.getContact());
		billingFromAddressToAppend.setHouseNo(billingAddress.getHouseNo());
		billingFromAddressToAppend.setCity(billingAddress.getCity());
		billingFromAddressToAppend.setState(billingAddress.getState());
		billingFromAddressToAppend.setCountry(billingAddress.getCountry());
		billingFromAddressToAppend.setLandmark(billingAddress.getLandmark());

		PostalCodes billCode = postalDao.getByCode(billPincode);
		if (billCode == null) {
			billCode = new PostalCodes();
			billCode.setPINCODE(billPincode);
			billCode.setActive(true);
			billCode = postalDao.save(billCode);
		}
		billingFromAddressToAppend.setPostalCode(billCode);
		billingFromAddressToAppend.setUser(userCurrent);
		// order will be assigned below and also flused down below at end

		// +++++++++++++++++++++++++++++++++++++
		// --------------------------

		// check if these cart belong to this user
		Optional<Usercart> userCartOptional = cartDao.findById(userCurrent.getCart().getId());
		if (userCartOptional.isEmpty()) {
			throw new UserException("Cart id invalid");
		}

		Usercart usercart = userCartOptional.get();

		// card vaild!
		CardCredential cardCredential = cardCredentialDao.matchCard(cardFormat.getCardNo(), cardFormat.getPin());
		if (cardCredential == null) {
			throw new ProductException("Invalid card");
		}

		// payment method
		Payment payment = paymentDao.findByMethodName("card");
		if (payment == null || !payment.isActive()) {
			throw new ProductException("Payment method not supported now");
		}
		// source payment
		PaymentSource paymentSource = paymentSourceDao.findByAccountInfo(cardFormat.getCardNo().toString());
		if (paymentSource == null) {
			paymentSource = new PaymentSource();
			paymentSource.setAccountInfo(cardFormat.getCardNo().toString());
			paymentSource = paymentSourceDao.save(paymentSource);
		}

		long orderTotal = 0;

		List<Orders> ordersList = new ArrayList<>();
		List<CartDetails> cartDetailsList = cartDetailsDao.findAll();
		for (CartDetails cartDetails : cartDetailsList) {
			// format created for new order
			Orders newOrders = new Orders();
			OrderDetails newOrderDetails = new OrderDetails();
			Products products = cartDetails.getProductCart();
			int quantuty = cartDetails.getQuantity();

			// value assigned
			newOrderDetails.setQuantity(quantuty);
			newOrderDetails.setProducts(products);
			products.getOrders().add(newOrderDetails); // ## reverse
			products.getCarts().remove(cartDetails); // cart details removed

			newOrders.setOrderDate(LocalDateTime.now());
			newOrders.setTotal_order_amount(quantuty * products.getSaleprice());
			newOrders.setPrepaid(true);

			newOrders.setPayments(payment);
			payment.getOrders().add(newOrders); // ## reverse

			newOrders.setCustomer(userCurrent);
			userCurrent.getOrders().add(newOrders); // ## reverse

			// address
			// billing
			newOrders.setBillingAddress(billingFromAddressToAppend);
			billingFromAddressToAppend.setOrders(newOrders); // ## reverse

			// shipping
			newOrders.setShippingAddress(shipToAddressToAppend);
			shipAddresses.setOrders(newOrders); // ## reverse

			newOrders.setOrderDetails(newOrderDetails);
			newOrderDetails.setOrders(newOrders); // order added to order details

			newOrders.setSource(paymentSource);
			paymentSource.getOrders().add(newOrders); // ## reverse

			ordersList.add(newOrders);
//					orderDao.save(newOrders);

			orderTotal += quantuty * products.getSaleprice();

		}
		if (orderTotal > cardCredential.getBalance()) {
			throw new ProductException("Insufficient balance");
		}
		cardCredential.setBalance(cardCredential.getBalance() - orderTotal);
		cardCredentialDao.save(cardCredential);

		for (Orders o : ordersList) {
			orderDao.save(o);
		}

		for (CartDetails cartDetails : cartDetailsList) {
			userCurrent.getCart().getCartDetails().remove(cartDetails);
			cartDetailsDao.delete(cartDetails);
		}
//				udao.save(userCurrent);
		return ordersList;
	}

	@Override
	public Orders placeOrderById(String sessionId, Integer userid, CardFormat cardFormat, Integer productId,
			Integer quantity, Addresses billingAddress, Addresses shipAddresses, Integer billPincode,
			Integer shipPinCode) throws UserException, LoginException, ProductException {
		// user valid!
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}

		if (cur.getUserId() != userid) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> user = udao.findById(userid);
		User userCurrent = user.get();

		// Address check
		// Address check
		// +++++++++++++++++++++++++++++++++++
		Addresses billingFromAddressToAppend = new Addresses();
		Addresses shipToAddressToAppend = new Addresses();

		// check if pincode serviceable for shipping
		PostalCodes shipCode = postalDao.getByCode(shipPinCode);
		if (shipCode == null || !shipCode.isActive()) {
			throw new ProductException("Product not deliverable to this localtion now");
		}

		// future upgrade analytic=> log this code as to see how many request came for a
		// perticular pincode
		shipToAddressToAppend.setName(shipAddresses.getName());
		shipToAddressToAppend.setContact(shipAddresses.getContact());
		shipToAddressToAppend.setHouseNo(shipAddresses.getHouseNo());
		shipToAddressToAppend.setCity(shipAddresses.getCity());
		shipToAddressToAppend.setState(shipAddresses.getState());
		shipToAddressToAppend.setCountry(shipAddresses.getCountry());
		shipToAddressToAppend.setLandmark(shipAddresses.getLandmark());

		if (shipCode == null) {
			shipCode = new PostalCodes();
			shipCode.setPINCODE(shipPinCode);
			shipCode.setActive(true);
			shipCode = postalDao.save(shipCode);
		}
		shipToAddressToAppend.setPostalCode(shipCode);
		shipToAddressToAppend.setUser(userCurrent);

		// billing from
		billingFromAddressToAppend.setName(billingAddress.getName());
		billingFromAddressToAppend.setContact(billingAddress.getContact());
		billingFromAddressToAppend.setHouseNo(billingAddress.getHouseNo());
		billingFromAddressToAppend.setCity(billingAddress.getCity());
		billingFromAddressToAppend.setState(billingAddress.getState());
		billingFromAddressToAppend.setCountry(billingAddress.getCountry());
		billingFromAddressToAppend.setLandmark(billingAddress.getLandmark());

		PostalCodes billCode = postalDao.getByCode(billPincode);
		if (billCode == null) {
			billCode = new PostalCodes();
			billCode.setPINCODE(billPincode);
			billCode.setActive(true);
			billCode = postalDao.save(billCode);
		}
		billingFromAddressToAppend.setPostalCode(billCode);
		billingFromAddressToAppend.setUser(userCurrent);
		// order will be assigned below and also flused down below at end

		// +++++++++++++++++++++++++++++++++++++
		// --------------------------

		// card vaild!
		CardCredential cardCredential = cardCredentialDao.matchCard(cardFormat.getCardNo(), cardFormat.getPin());
		if (cardCredential == null) {
			throw new ProductException("Invalid card");
		}

		// payment method
		Payment payment = paymentDao.findByMethodName("card");
		if (payment == null || !payment.isActive()) {
			throw new ProductException("Payment method not supported now");
		}

		// Product verification
		Optional<Products> productsOptional = pdao.findById(productId);
		if (productsOptional.isEmpty()) {
			throw new ProductException("Product doesn't exist");
		}
		Products products = productsOptional.get();

		// sufficient / insufficient balance?
		double orderTotal = products.getSaleprice() * quantity;
		if (cardCredential.getBalance() < quantity * products.getSaleprice()) {
			throw new ProductException("Insufficient balance");
		}
		// balance updating for the payment method
		cardCredential.setBalance(cardCredential.getBalance() - orderTotal);
		cardCredentialDao.save(cardCredential);

		// source payment
		PaymentSource paymentSource = paymentSourceDao.findByAccountInfo(cardFormat.getCardNo().toString());
		if (paymentSource == null) {
			paymentSource = new PaymentSource();
			paymentSource.setAccountInfo(cardFormat.getCardNo().toString());
			paymentSource = paymentSourceDao.save(paymentSource);
		}

		// Order entry ================================================
		// ============================================================
		Orders newOrders = new Orders(); // new order entry file
		OrderDetails newOrderDetails = new OrderDetails(); // new order details entry file

		// value assigned
		newOrderDetails.setQuantity(quantity);
		newOrderDetails.setProducts(products);
		products.getOrders().add(newOrderDetails); // ## reverse

		newOrders.setOrderDate(LocalDateTime.now());
		newOrders.setTotal_order_amount(quantity * products.getSaleprice());
		newOrders.setPrepaid(true);

		newOrders.setPayments(payment);
		payment.getOrders().add(newOrders); // ## reverse

		newOrders.setCustomer(userCurrent);
		userCurrent.getOrders().add(newOrders); // ## reverse

		// address
		// billing
		newOrders.setBillingAddress(billingFromAddressToAppend);
		billingFromAddressToAppend.setOrders(newOrders); // ## reverse

		// shipping
		newOrders.setShippingAddress(shipToAddressToAppend);
		shipAddresses.setOrders(newOrders); // ## reverse

		newOrders.setOrderDetails(newOrderDetails);
		newOrderDetails.setOrders(newOrders); // order added to order details

		newOrders.setSource(paymentSource);
		paymentSource.getOrders().add(newOrders); // ## reverse

		newOrders = orderDao.save(newOrders);
		return newOrders;
	}

	@Override
	public Orders viewOrderById(String sessionId, Integer userid, Integer orderId)
			throws UserException, LoginException, OrderException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}

		if (cur.getUserId() != userid) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> user = udao.findById(userid);
		User userCurrent = user.get();

		Orders orders = orderDao.findOrderById(userCurrent.getUserId(), orderId);
		if (orders == null) {
			throw new OrderException("Order not exists with id " + orderId);
		}
		return orders;
	}

	@Override
	public List<Orders> viewAllOrder(String sessionId, Integer userid)
			throws UserException, LoginException, OrderException {
		CurrentSession cur = sessionDao.findByUuid(sessionId);
		if (cur == null) {
			throw new LoginException("Please log in to post");
		}

		if (cur.getUserId() != userid) {
			throw new LoginException("Please login with your account");
		}

		Optional<User> user = udao.findById(userid);
		User userCurrent = user.get();

		List<Orders> listOfOrders = orderDao.findTop5ByOrderByOrderIdDesc(); // userCurrent.getOrders();
		return listOfOrders;
	}

}
