package com.green.biz.order.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.order.CartVO;

@Repository
public class CartDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 장바구니에 담기
	public void insertCart(CartVO cVo) {
		System.out.println("===> mybatis로 insertCart() 기능 처리");
		
		mybatis.insert("CartDAO.insertCart", cVo);
	}
	
	// 장바구니 목록
	public List<CartVO> listCart(String userId){
		System.out.println("===> mybatis로 listCart() 기능 처리");
		
		
		return mybatis.selectList("CartDAO.listCart", userId);
	}
	
	// 장바구니 취소
	public void deleteCart(int cseq) {
		System.out.println("===> mybatis로 deleteCart() 기능 처리");
		
		mybatis.delete("CartDAO.deleteCart", cseq);
	}
	
	// 장바구니 갱신
	public void updateCart(int cseq) {
		System.out.println("===> mybatis로 updateCart() 기능 처리");
		
		mybatis.update("CartDAO.updateCart", cseq);
	}
}
