package com.green.biz.order;

import java.util.List;

public interface OrderService {
	int selectMaxOseq();
	int insertOrder(OrderVO oVo);
	void insertOrderDetail(OrderVO oVo);
	List<OrderVO> listOrderById(OrderVO oVo);
	List<Integer> selectSeqOrdering(String id);
	List<OrderVO> listOrder(OrderVO oVo);
	void updateOrderResult(OrderVO oVo);
}
