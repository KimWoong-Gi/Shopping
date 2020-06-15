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
	
	// 리턴 값 = 주문 일련번호
	@Override
	public int insertOrder(OrderVO oVo) {
		
		// 주문번호를 할당받는다.
		int maxOseq = selectMaxOseq();
		
		oVo.setOseq(maxOseq);
		
		// 주문 테이블에 주문벌호와 아이디를 저장한다.
		oDao.insertOrder(oVo);
		
		// 장바구니에서 목록을 가져온다.
		List<CartVO> cartList = cartService.listCart(oVo.getId());
		System.out.println("아이디 출력 테스트"+oVo.getId());
		
		// 장바구니에 있는 주문상세 내역을 order_detail 테이블에 저장한다.
		//OrderVO vo = new OrderVO();
		
		for (CartVO cVo : cartList) {
			System.out.println("장바구니 내역" + cVo);
			
			OrderVO vo = new OrderVO();
			
			vo.setOseq(maxOseq); // 주문번호 설정
			vo.setPseq(cVo.getPseq()); // 상품번호 설정
			vo.setQuantity(cVo.getQuantity()); // 수량 설정
			
			insertOrderDetail(vo);
			
			// cart 테이블에 주문처리를 완료로 수정한다.
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
