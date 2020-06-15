package com.green.biz.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.order.CartService;
import com.green.biz.order.CartVO;
import com.green.biz.order.OrderService;
import com.green.biz.order.OrderVO;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderDAO oDao;
	@Autowired
	private CartService cartService;
	
	@Override
	public int selectMaxOseq() {
		
		return oDao.selectMaxOseq();
	}
	
	// ���� �� = �ֹ� �Ϸù�ȣ
	@Override
	public int insertOrder(OrderVO oVo) {
		
		// �ֹ���ȣ�� �Ҵ�޴´�.
		int maxOseq = selectMaxOseq();
		
		oVo.setOseq(maxOseq);
		
		// �ֹ� ���̺� �ֹ���ȣ�� ���̵� �����Ѵ�.
		oDao.insertOrder(oVo);
		
		// ��ٱ��Ͽ��� ����� �����´�.
		List<CartVO> cartList = cartService.listCart(oVo.getId());
		System.out.println("���̵� ��� �׽�Ʈ"+oVo.getId());
		
		// ��ٱ��Ͽ� �ִ� �ֹ��� ������ order_detail ���̺� �����Ѵ�.
		//OrderVO vo = new OrderVO();
		
		for (CartVO cVo : cartList) {
			System.out.println("��ٱ��� ����" + cVo);
			
			OrderVO vo = new OrderVO();
			
			vo.setOseq(maxOseq); // �ֹ���ȣ ����
			vo.setPseq(cVo.getPseq()); // ��ǰ��ȣ ����
			vo.setQuantity(cVo.getQuantity()); // ���� ����
			
			insertOrderDetail(vo);
			
			// cart ���̺� �ֹ�ó���� �Ϸ�� �����Ѵ�.
			cartService.updateCart(cVo.getCseq());
		}
		
		return maxOseq;
	}

	@Override
	public void insertOrderDetail(OrderVO oVo) {
		
		oDao.insertOrderDetail(oVo);
	}

	@Override
	public List<OrderVO> listOrderById(OrderVO oVo) {
		
		return oDao.listOrderById(oVo);
	}

	@Override
	public List<Integer> selectSeqOrdering(String id) {
		
		return oDao.selectSeqOrdering(id);
	}

	@Override
	public List<OrderVO> listOrder(OrderVO oVo) {
		
		return oDao.listOrder(oVo);
	}

	@Override
	public void updateOrderResult(OrderVO oVo) {
		
		oDao.updateOrderResult(oVo);
	}

}
