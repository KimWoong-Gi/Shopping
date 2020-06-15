package com.green.biz.order;

import java.util.List;

public interface CartService {
	void insertCart(CartVO cVo);
	List<CartVO> listCart(String userId);
	void deleteCart(int cseq);
	void updateCart(int cseq);
}
