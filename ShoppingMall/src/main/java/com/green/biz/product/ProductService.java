package com.green.biz.product;

import java.util.List;

import com.green.biz.utils.Criteria;

public interface ProductService {
	
	ProductVO getProduct(ProductVO pVo);
	List<ProductVO> getNewProductList();
	List<ProductVO> getBestProductList();
	List<ProductVO> getProductListByKind(ProductVO pVo);
	
	int countProductList(String name);
	List<ProductVO> listProduct(String name);
	List<ProductVO> listWithPaging(Criteria criteria, String name);
	void insertProduct(ProductVO pVo);
	void updateProduct(ProductVO pVo);
	
	List<CommentVO> getCommentList(int pseq);
	void insertComment(CommentVO cVo);
	void updateComment(CommentVO cVo);
	void deleteComment(int cseq);
	
	List<SalesQuantityVO> getProductSales();
}
