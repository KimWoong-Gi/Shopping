package com.green.biz.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.product.CommentVO;
import com.green.biz.product.ProductService;
import com.green.biz.product.ProductVO;
import com.green.biz.product.SalesQuantityVO;
import com.green.biz.utils.Criteria;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;

	@Override
	public ProductVO getProduct(ProductVO pVo) {

		return productDAO.getProduct(pVo);
	}

	@Override
	public List<ProductVO> getNewProductList() {

		return productDAO.getNewProductList();
	}

	@Override
	public List<ProductVO> getBestProductList() {

		return productDAO.getBestProductList();
	}

	@Override
	public List<ProductVO> getProductListByKind(ProductVO pVo) {

		return productDAO.getProductListByKind(pVo);
	}

	@Override
	public int countProductList(String name) {

		return productDAO.countProductList(name);
	}

	@Override
	public List<ProductVO> listProduct(String name) {

		return productDAO.listProduct(name);
	}

	@Override
	public void insertProduct(ProductVO pVo) {

		productDAO.insertProduct(pVo);
	}

	@Override
	public void updateProduct(ProductVO pVo) {

		productDAO.updateProduct(pVo);
	}

	@Override
	public List<ProductVO> listWithPaging(Criteria criteria, String name) {

		return productDAO.listWithPaging(criteria, name);
	}

	@Override
	public List<CommentVO> getCommentList(int pseq) {

		return productDAO.listComment(pseq);
	}

	@Override
	public void insertComment(CommentVO cVo) {

		productDAO.insertComment(cVo);
	}

	@Override
	public void updateComment(CommentVO cVo) {

		productDAO.updateComment(cVo);
	}

	@Override
	public void deleteComment(int cseq) {

		productDAO.deleteComment(cseq);
	}
	
	@Override
	public List<SalesQuantityVO> getProductSales() {
		
		return productDAO.getProductSales();
	}
}
