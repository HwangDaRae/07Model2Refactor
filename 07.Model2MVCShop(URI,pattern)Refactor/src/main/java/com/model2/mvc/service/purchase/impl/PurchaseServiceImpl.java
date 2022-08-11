package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;

	public PurchaseServiceImpl() {
		System.out.println("PurchaseServiceImpl default constructor");
	}

	@Override
	public Purchase addPurchase(Purchase purchase, Product product) throws Exception {
		System.out.println("PurchaseServiceImpl addPurchase(PurchaseVO purchaseVO) start...");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purchase", purchase);
		return purchaseDao.addPurchase(purchase);
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		System.out.println("PurchaseServiceImpl getPurchase(int tranNo) start...");
		return purchaseDao.getPurchase(tranNo);
	}

	@Override
	public List<Purchase> getPurchaseFromTranId(String tranId) throws Exception {
		System.out.println("PurchaseServiceImpl getPurchaseFromTranId(int tranId) start...");
		return purchaseDao.getPurchaseFromTranId(tranId);
	}

	@Override
	public int totalCountPurchaseList(String userId) throws Exception {
		return purchaseDao.totalCountPurchaseList(userId);
	}

	@Override
	public List<Purchase> getPurchaseList(Search searchVO, String userId) throws Exception {
		System.out.println("PurchaseServiceImpl getPurchaseList(SearchVO searchVO) start...");
		return purchaseDao.getPurchaseList(searchVO, userId);
	}

	@Override
	public Purchase updatePurchase(Purchase purchaseVO) throws Exception {
		System.out.println("PurchaseServiceImpl updatePurchase(PurchaseVO purchaseVO) start...");
		return purchaseDao.updatePurchase(purchaseVO);
	}

	@Override
	public void updateTranCode(Map<String, Object> map) throws Exception {
		System.out.println("PurchaseServiceImpl updateTranCode(PurchaseVO purchaseVO) start...");
		purchaseDao.updateTranCode(map);
	}

}
