package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSession sqlSession;

	public PurchaseDaoImpl() {
		System.out.println("¿©±â´Â PurchaseDAOImpl default Constructor");
	}
	
	@Override
	public Purchase addPurchase(Purchase purchase) throws Exception {
		System.out.println("PurchaseDAOImpl addPurchase(Purchase purchaseVO, Product productVO)");
		int i = sqlSession.insert("PurchaseMapper.addPurchase", purchase);
		if(i==1) {
			return (Purchase)sqlSession.selectOne("PurchaseMapper.findPurchase", purchase.getTranNo());
		}else {
			return null;
		}
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		System.out.println("PurchaseDaoImpl getPurchase(int tranNo) start...");
		return sqlSession.selectOne("PurchaseMapper.findPurchase", tranNo);
	}

	@Override
	public List<Purchase> getPurchaseFromTranId(String tranId) throws Exception {
		return sqlSession.selectList("PurchaseMapper.findPurchaseFromTranId", tranId);
	}

	@Override
	public int totalCountPurchaseList(String userId) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.totalCount", userId);
	}

	@Override
	public List<Purchase> getPurchaseList(Search searchVO, String userId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", searchVO);
		map.put("userId", userId);
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
	}

	@Override
	public Purchase updatePurchase(Purchase purchaseVO) throws Exception {
		int i = sqlSession.update("PurchaseMapper.updatePurchase", purchaseVO);
		if(i==1) {
			return sqlSession.selectOne("PurchaseMapper.findPurchase", purchaseVO.getTranNo());
		}else {
			return null;
		}
	}

	@Override
	public void updateTranCode(Map<String, Object> map) throws Exception {
		sqlSession.update("PurchaseMapper.updateTranCode", map);
	}

}
