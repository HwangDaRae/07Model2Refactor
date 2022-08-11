package com.model2.mvc.service.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.ProductDao;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	@Qualifier("productDAOImpl")
	private ProductDao productDAO;
	
	public ProductServiceImpl() {
		System.out.println("ProductServiceImpl default constructor");
	}

	@Override
	public Product addProduct(Product productVO) throws Exception {
		System.out.println("ProductServiceImpl addProduct(ProductVO productVO)");
		return productDAO.addProduct(productVO);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		System.out.println("ProductServiceImpl getProduct(int prodNo)");
		return productDAO.getProduct(prodNo);
	}

	@Override
	public int getProductTotalCount(Search search) throws Exception {
		return productDAO.getProductTotalCount(search);
	}

	@Override
	public List<Product> getProductList(Search searchVO) throws Exception {
		System.out.println("ProductServiceImpl getProductList(SearchVO searchVO)");		
		return productDAO.getProductList(searchVO);
	}

	@Override
	public Product updateProduct(Product productVO) throws Exception {
		System.out.println("ProductServiceImpl updateProduct(ProductVO productVO)");
		return productDAO.updateProduct(productVO);
	}

	@Override
	public Product getNonMemberPurchase(int tranNo) throws Exception {
		System.out.println("PurchaseServiceImpl getNonMemberPurchase(int tranNo) start...");
		return productDAO.getNonMemberPurchase(tranNo);
	}

}
