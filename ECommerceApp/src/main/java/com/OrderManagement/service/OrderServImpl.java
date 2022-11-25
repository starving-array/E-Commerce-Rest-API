package com.OrderManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrderManagement.Admin.module.CardCredential;
import com.OrderManagement.Admin.repository.CardCredentialDao;
import com.OrderManagement.DTO.OrderDto;
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
import com.OrderManagement.paymentMethods.OrderFormFormat;
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
	public List<OrderDto> placeCartOrderPertial(List<Integer> cartIds, String sessionId, Integer userid, Long cardNo,
			Integer cardPin, Integer usercartId, Addresses shipAddresses, Integer shipPinCode)
			throws ProductException, LoginException, UserException {

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
		Addresses shipToAddressToAppend = new Addresses();
		// future upgrade analytic=> log this code as to see how many request came for a
		// perticular pincode
		shipToAddressToAppend.setName(shipAddresses.getName());
		shipToAddressToAppend.setContact(shipAddresses.getContact());
		shipToAddressToAppend.setHouseNo(shipAddresses.getHouseNo());
		shipToAddressToAppend.setCity(shipAddresses.getCity());
		shipToAddressToAppend.setState(shipAddresses.getState());
		shipToAddressToAppend.setCountry(shipAddresses.getCountry());
		shipToAddressToAppend.setLandmark(shipAddresses.getLandmark());
		// check if pincode serviceable for shipping
		PostalCodes shipCode = postalDao.getByCode(shipPinCode);
		if (shipCode == null || !shipCode.isActive()) {
			throw new ProductException("Product not deliverable to this localtion now");
		}

		shipToAddressToAppend.setPostalCode(shipCode);
		shipCode.getAddresses().add(shipToAddressToAppend); // reverse

		shipToAddressToAppend = addressDao.save(shipToAddressToAppend); // flushing

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
		CardCredential cardCredential = cardCredentialDao.matchCard(cardNo, cardPin);
		if (cardCredential == null) {
			throw new ProductException("Invalid card");
		}

		// payment method
		Payment payment = paymentDao.findByMethodName("card");
		if (payment == null || !payment.isActive()) {
			throw new ProductException("Payment method not supported now");
		}
		// source payment
		PaymentSource paymentSource = paymentSourceDao.findByAccountInfo(cardNo.toString());
		if (paymentSource == null) {
			paymentSource = new PaymentSource();
			paymentSource.setAccountInfo(cardNo.toString());
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
			// shipping
			newOrders.setShippingAddress(shipToAddressToAppend);
			shipAddresses.getOrders().add(newOrders); // ## reverse

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

		for (int i = 0; i < ordersList.size(); i++) {
			ordersList.set(i, orderDao.save(ordersList.get(i)));
		}

		for (Integer id : cartIds) {
			Optional<CartDetails> cartOptional = cartDetailsDao.findById(id);
			CartDetails cartDetails = cartOptional.get();
			userCurrent.getCart().getCartDetails().remove(cartDetails);
			cartDetailsDao.delete(cartDetails);
		}

		List<OrderDto> listDtoOrder = new ArrayList<>();
		for (Orders o : ordersList) {
			listDtoOrder.add(new OrderDto().getDtoOrder(o));
		}
		return listDtoOrder;
	}

	@Override
	public List<OrderDto> placeAllCartOrder(String sessionId, Integer userid, Long cardNo, Integer cardPin,
			Addresses shipAddresses, Integer shipPinCode) throws ProductException, LoginException, UserException {

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

		// future upgrade analytic=> log this code as to see how many request came for a
		// perticular pincode
		shipToAddressToAppend.setName(shipAddresses.getName());
		shipToAddressToAppend.setContact(shipAddresses.getContact());
		shipToAddressToAppend.setHouseNo(shipAddresses.getHouseNo());
		shipToAddressToAppend.setCity(shipAddresses.getCity());
		shipToAddressToAppend.setState(shipAddresses.getState());
		shipToAddressToAppend.setCountry(shipAddresses.getCountry());
		shipToAddressToAppend.setLandmark(shipAddresses.getLandmark());
		// check if pincode serviceable for shipping
		PostalCodes shipCode = postalDao.getByCode(shipPinCode);
		if (shipCode == null || !shipCode.isActive()) {
			throw new ProductException("Product not deliverable to this localtion now");
		}
		shipToAddressToAppend.setPostalCode(shipCode);
		shipCode.getAddresses().add(shipToAddressToAppend); // reverse

		shipToAddressToAppend = addressDao.save(shipToAddressToAppend); // flushing
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
		CardCredential cardCredential = cardCredentialDao.matchCard(cardNo, cardPin);
		if (cardCredential == null) {
			throw new ProductException("Invalid card");
		}

		// payment method
		Payment payment = paymentDao.findByMethodName("card");
		if (payment == null || !payment.isActive()) {
			throw new ProductException("Payment method not supported now");
		}
		// source payment
		PaymentSource paymentSource = paymentSourceDao.findByAccountInfo(cardNo.toString());
		if (paymentSource == null) {
			paymentSource = new PaymentSource();
			paymentSource.setAccountInfo(cardNo.toString());
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
			// shipping
			newOrders.setShippingAddress(shipToAddressToAppend);
			shipAddresses.getOrders().add(newOrders); // ## reverse

			newOrders.setOrderDetails(newOrderDetails);
			newOrderDetails.setOrders(newOrders); // order added to order details

			newOrders.setSource(paymentSource);
			paymentSource.getOrders().add(newOrders); // ## reverse

			ordersList.add(newOrders);
//					orderDao.save(newOrders);

			orderTotal += quantuty * products.getSaleprice();

		}

		// check if blalance if sufficient
		if (orderTotal > cardCredential.getBalance()) {
			throw new ProductException("Insufficient balance");
		}

		cardCredential.setBalance(cardCredential.getBalance() - orderTotal);
		cardCredentialDao.save(cardCredential);

		for (int i = 0; i < ordersList.size(); i++) {
			ordersList.set(i, orderDao.save(ordersList.get(i)));
		}

		for (CartDetails cartDetails : cartDetailsList) {
			userCurrent.getCart().getCartDetails().remove(cartDetails);
			cartDetailsDao.delete(cartDetails);
		}
		List<OrderDto> listDtoOrder = new ArrayList<>();
		for (Orders o : ordersList) {
			listDtoOrder.add(new OrderDto().getDtoOrder(o));
		}
		return listDtoOrder;
	}

	@Override
	public OrderDto placeOrderById(String sessionId, Integer userid, Long cardNo, Integer cardPin, Integer productId,
			Integer quantity, Addresses shipAddresses, Integer shipPinCode)
			throws UserException, LoginException, ProductException {
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
		Addresses shipToAddressToAppend = new Addresses();
		// perticular pincode
		shipToAddressToAppend.setName(shipAddresses.getName());
		shipToAddressToAppend.setContact(shipAddresses.getContact());
		shipToAddressToAppend.setHouseNo(shipAddresses.getHouseNo());
		shipToAddressToAppend.setCity(shipAddresses.getCity());
		shipToAddressToAppend.setState(shipAddresses.getState());
		shipToAddressToAppend.setCountry(shipAddresses.getCountry());
		shipToAddressToAppend.setLandmark(shipAddresses.getLandmark());

		// check if pincode serviceable for shipping
		PostalCodes shipCode = postalDao.getByCode(shipPinCode);
		if (shipCode == null || !shipCode.isActive()) {
			throw new ProductException("Product not deliverable to this localtion now");
		}

		shipToAddressToAppend.setPostalCode(shipCode);
		shipCode.getAddresses().add(shipToAddressToAppend); // reverse

		shipToAddressToAppend = addressDao.save(shipToAddressToAppend); // flushing
		// order will be assigned below and also flused down below at end

		// +++++++++++++++++++++++++++++++++++++
		// --------------------------

		// card vaild!
		CardCredential cardCredential = cardCredentialDao.matchCard(cardNo, cardPin);
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
		if (cardCredential.getBalance() < orderTotal) {
			throw new ProductException("Insufficient balance");
		}
		// balance updating for the payment method
		cardCredential.setBalance(cardCredential.getBalance() - orderTotal);
		cardCredentialDao.save(cardCredential);

		// source payment
		PaymentSource paymentSource = paymentSourceDao.findByAccountInfo(cardNo.toString());
		if (paymentSource == null) {
			paymentSource = new PaymentSource();
			paymentSource.setAccountInfo(cardNo.toString());
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
		// shipping
		newOrders.setShippingAddress(shipToAddressToAppend);
		shipAddresses.getOrders().add(newOrders); // ## reverse

		newOrders.setOrderDetails(newOrderDetails);
		newOrderDetails.setOrders(newOrders); // order added to order details

		newOrders.setSource(paymentSource);
		paymentSource.getOrders().add(newOrders); // ## reverse

		newOrders = orderDao.save(newOrders);

		OrderDto orderDto = new OrderDto().getDtoOrder(newOrders);

		return orderDto;
	}

	@Override
	public OrderDto viewOrderById(String sessionId, Integer userid, Integer orderId)
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
		OrderDto orderDto = new OrderDto().getDtoOrder(orders);

		return orderDto;
	}

	@Override
	public List<OrderDto> viewAllOrder(String sessionId, Integer userid)
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
		List<OrderDto> listDtoOrder = new ArrayList<>();
		for (Orders o : listOfOrders) {
			listDtoOrder.add(new OrderDto().getDtoOrder(o));
		}
		return listDtoOrder;
	}

}
