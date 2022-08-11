package com.model2.mvc.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;

@Repository("productDAOImpl")
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSession sqlSession;

	public ProductDaoImpl() {
		System.out.println("여기는 ProductDAOImpl default Constructor");
	}

	@Override
	public Product addProduct(Product productVO) throws Exception {
		System.out.println(getClass() + ".addProduct(Product productVO)");
		int i = sqlSession.insert("ProductMapper.addProduct", productVO);
		System.out.println(i);
		if(i==1) {
			return productVO;
		}else {
			return null;
		}
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		System.out.println(getClass() + ".getProduct(int prodNo)");
		return sqlSession.selectOne("ProductMapper.findProduct", prodNo);
	}

	@Override
	public int getProductTotalCount(Search search) throws Exception {
		System.out.println(getClass() + ".getProductTotalCount(Search search)");
		return sqlSession.selectOne("ProductMapper.totalCountProduct", search);
	}

	@Override
	public List<Product> getProductList(Search searchVO) throws Exception {
		System.out.println(getClass() + ".getProductList(Search searchVO)");
		System.out.println(searchVO);
		return sqlSession.selectList("ProductMapper.allProduct", searchVO);
	}

	@Override
	public Product updateProduct(Product productVO) throws Exception {
		System.out.println(getClass() + ".updateProduct(Product productVO)");
		int i = sqlSession.update("ProductMapper.updateProduct", productVO);
		if(i==1) {
			System.out.println("상품 수정 성공");
			return sqlSession.selectOne("ProductMapper.findProduct", productVO.getProdNo());
		}else {
			System.out.println("상품 수정 실패");
			return null;
		}
	}

	@Override
	public Product getNonMemberPurchase(int tranNo) throws Exception {
		System.out.println(getClass() + ".getNonMemberPurchase(int tranNo)");
		return sqlSession.selectOne("ProductMapper.getNonMemberPurchase", tranNo);
	}

}
