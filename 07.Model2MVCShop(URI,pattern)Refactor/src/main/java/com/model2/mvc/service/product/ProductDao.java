package com.model2.mvc.service.product;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductDao {
	
	public Product addProduct(Product productVO) throws Exception;
	
	public Product getProduct(int prodNo) throws Exception;
	
	public int getProductTotalCount(Search search) throws Exception;
	
	public List<Product> getProductList(Search searchVO) throws Exception;
	
	public Product updateProduct(Product productVO) throws Exception;
	
	public Product getNonMemberPurchase(int tranNo) throws Exception;
}


