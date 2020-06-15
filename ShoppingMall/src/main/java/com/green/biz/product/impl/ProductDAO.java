package com.green.biz.product.impl;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.product.CommentVO;
import com.green.biz.product.ProductVO;
import com.green.biz.product.SalesQuantityVO;
import com.green.biz.utils.Criteria;

@Repository
public class ProductDAO {

	@Autowired
	private SqlSessionTemplate mybatis;

	public ProductVO getProduct(ProductVO pVo) {
		System.out.println("==> Mybatis로 getProduct() 기능 처리");

		return mybatis.selectOne("ProductDAO.getProduct", pVo);
	}

	public List<ProductVO> getNewProductList() {
		System.out.println("==> Mybatis로 getNewProductList() 기능 처리");

		return mybatis.selectList("ProductDAO.getNewProductList");
	}

	public List<ProductVO> getBestProductList() {
		System.out.println("==> Mybatis로 getBestProductList() 기능 처리");

		return mybatis.selectList("ProductDAO.getBestProductList");
	}

	/*
	 * 카테고리별 조회
	 */
	public List<ProductVO> getProductListByKind(ProductVO pVo) {
		System.out.println("==> Mybatis로 getProductListByKind() 기능 처리");

		return mybatis.selectList("ProductDAO.getProductListByKind", pVo);
	}

	// 상품 개수 조회
	public int countProductList(String name) {
		return mybatis.selectOne("ProductDAO.countProductList", name);
	}

	// 페이지별 상품 목록 조회
	public List<ProductVO> listWithPaging(Criteria criteria, String name) {
		System.out.println("==> Mybatis로 listWithPaging() 기능 처리");

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("criteria", criteria);

		return mybatis.selectList("ProductDAO.listWithPaging", map);
	}

	public List<ProductVO> listProduct(String name) {

		return mybatis.selectList("ProductDAO.listProduct", name);
	}

	public void insertProduct(ProductVO pVo) {
		mybatis.insert("ProductDAO.insertProduct", pVo);
	}

	public void updateProduct(ProductVO pVo) {
		mybatis.update("ProductDAO.updateProduct", pVo);
	}

	// 상품평 관련 메소드
	public List<CommentVO> listComment(int pseq) {

		return mybatis.selectList("CommentDAO.getCommentList", pseq);
	}

	public void insertComment(CommentVO cVo) {

		mybatis.insert("CommentDAO.insertComment", cVo);
	}

	public void updateComment(CommentVO cVo) {

		mybatis.update("CommentDAO.updateComment", cVo);
	}

	public void deleteComment(int cseq) {

		mybatis.delete("CommentDAO.deleteComment", cseq);
	}

	public List<SalesQuantityVO> getProductSales() {

		return mybatis.selectList("ProductDAO.getProductSales");
	}

}
