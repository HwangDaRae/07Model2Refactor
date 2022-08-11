package com.model2.mvc.service.cart;

import java.util.List;
import java.util.Map;

import com.model2.mvc.service.domain.Cart;

public interface CartService {
	
	public void insertCart(Cart cart) throws Exception;
	
	public void deleteCart(Map<String,Object> map) throws Exception;
	
	public void updateAmount(Cart cart) throws Exception;
	
	public int totalCountCartList(String user_id) throws Exception;
	
	public List<Cart> getCartList(String user_id) throws Exception;

}
