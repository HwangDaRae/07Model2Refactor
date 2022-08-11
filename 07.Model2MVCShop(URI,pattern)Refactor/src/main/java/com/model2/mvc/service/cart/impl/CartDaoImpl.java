package com.model2.mvc.service.cart.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.service.cart.CartDao;
import com.model2.mvc.service.domain.Cart;

@Repository("cartDaoImpl")
public class CartDaoImpl implements CartDao {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	public CartDaoImpl() {
		System.out.println("¿©±â´Â CartDaoImpl default Constructor");
	}

	@Override
	public void insertCart(Cart cart) throws Exception {
		sqlSession.insert("CartMapper.insertCart", cart);
	}

	@Override
	public void deleteCart(Map<String, Object> map) throws Exception {
		sqlSession.delete("CartMapper.deleteCart", map);
	}

	@Override
	public void updateAmount(Cart cart) throws Exception {
		sqlSession.update("CartMapper.updateAmount", cart);
	}

	@Override
	public int totalCountCartList(String user_id) throws Exception {
		return sqlSession.selectOne("CartMapper.totalCountCartList", user_id);
	}

	@Override
	public List<Cart> getCartList(String user_id) throws Exception {
		return sqlSession.selectList("CartMapper.getCartList", user_id);
	}

}
