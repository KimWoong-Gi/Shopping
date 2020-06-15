package com.green.biz.order.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.order.CartVO;
import com.green.biz.order.OrderVO;

@Repository
public class OrderDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int selectMaxOseq() {
		return mybatis.selectOne("OrderDAO.selectMaxOseq");
	}
	
	public void insertOrder(OrderVO oVo) {
		mybatis.insert("OrderDAO.insertOrder", oVo);
	}
	
	public void insertOrderDetail(OrderVO oVo) {
		mybatis.insert("OrderDAO.insertOrderDetail", oVo);
	}
	
	public List<OrderVO> listOrderById(OrderVO oVo){
		return mybatis.selectList("OrderDAO.listOrderById", oVo);
	}
	
	public List<Integer> selectSeqOrdering(String id){
		return mybatis.selectList("OrderDAO.selectSeqOrdering", id);
	}
	
	public List<OrderVO> listOrder(OrderVO oVo){
		return mybatis.selectList("OrderDAO.listOrder", oVo);
	}
	
	public void updateOrderResult(OrderVO oVo) {
		mybatis.update("OrderDAO.updateOrderResult", oVo);
	}
}

