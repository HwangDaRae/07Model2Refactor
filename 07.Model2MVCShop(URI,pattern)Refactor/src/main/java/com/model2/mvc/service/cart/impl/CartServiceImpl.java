package com.model2.mvc.service.cart.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.service.cart.CartDao;
import com.model2.mvc.service.cart.CartService;
import com.model2.mvc.service.domain.Cart;

@Service("cartServiceImpl")
public class CartServiceImpl implements CartService {
	
	@Autowired
	@Qualifier("cartDaoImpl")
	CartDao cartDao;

	public CartServiceImpl() {
		System.out.println("CartServiceImpl default constructor");
	}

	@Override
	public void insertCart(Cart cart) throws Exception {
		 System.out.println("CartServiceImpl insertCart(Cart cart)");
		 cartDao.insertCart(cart);
	}

	@Override
	public void deleteCart(Map<String, Object> map) throws Exception {
		System.out.println("CartServiceImpl deleteCart(Map<String,Object> map)");
		cartDao.deleteCart(map);
	}

	@Override
	public void updateAmount(Cart cart) throws Exception {
		System.out.println("CartServiceImpl updateAmount(Cart cart)");
		cartDao.updateAmount(cart);
	}

	@Override
	public int totalCountCartList(String user_id) throws Exception {
		System.out.println("CartServiceImpl totalCountCartList(String user_id)");
		return cartDao.totalCountCartList(user_id);
	}

	@Override
	public List<Cart> getCartList(String user_id) throws Exception {
		System.out.println("CartServiceImpl getCartList(String user_id)");
		return cartDao.getCartList(user_id);
	}

}
