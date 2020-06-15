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
	
	// ��ٱ��Ͽ� ���
	public void insertCart(CartVO cVo) {
		System.out.println("===> mybatis�� insertCart() ��� ó��");
		
		mybatis.insert("CartDAO.insertCart", cVo);
	}
	
	// ��ٱ��� ���
	public List<CartVO> listCart(String userId){
		System.out.println("===> mybatis�� listCart() ��� ó��");
		
		
		return mybatis.selectList("CartDAO.listCart", userId);
	}
	
	// ��ٱ��� ���
	public void deleteCart(int cseq) {
		System.out.println("===> mybatis�� deleteCart() ��� ó��");
		
		mybatis.delete("CartDAO.deleteCart", cseq);
	}
	
	// ��ٱ��� ����
	public void updateCart(int cseq) {
		System.out.println("===> mybatis�� updateCart() ��� ó��");
		
		mybatis.update("CartDAO.updateCart", cseq);
	}
}
