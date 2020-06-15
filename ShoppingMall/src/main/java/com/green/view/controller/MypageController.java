package com.green.view.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.biz.member.MemberVO;
import com.green.biz.order.CartService;
import com.green.biz.order.CartVO;
import com.green.biz.order.OrderService;
import com.green.biz.order.OrderVO;

@Controller
public class MypageController {
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "cart_insert", method = RequestMethod.POST)
	public String cartInsert(CartVO cVo, HttpServletRequest request) {

		String result = "";

		MemberVO user = (MemberVO) request.getSession().getAttribute("loginUser");

		if (user != null) {

			cVo.setId(user.getId());

			cartService.insertCart(cVo);

			result = "redirect:cart_list"; // 사용자 화면에서 Servlet으로 action명을 요청하는 문자열
		} else {
			// 로그인이 안되어 있으면 로그인 화면 표시
			result = "member/login";
		}

		return result;
	}

	@RequestMapping(value = "cart_list", method = RequestMethod.GET)
	public String cartList(Model model, HttpSession session) { // 위에는 request를 매개변수로 받아서 session 값을 얻어왔지만 요렇게 해줘도 무방하다.

		String result = "";
		MemberVO user = (MemberVO) session.getAttribute("loginUser");

		if (user != null) {

			List<CartVO> cartList = cartService.listCart(user.getId());

			int total = 0;

			for (CartVO cartVO : cartList) {
				total += cartVO.getPrice2() * cartVO.getQuantity();
			}

			model.addAttribute("cartList", cartList);
			model.addAttribute("totalPrice", total);

			result = "mypage/cartList";

		} else {

			result = "member/login";
		}

		return result;
	}

	@RequestMapping(value = "cart_update", method = RequestMethod.POST)
	public String cartUpdate(CartVO cVo) {

		cartService.updateCart(cVo.getCseq());

		return "mypage/cartList";
	}

	@RequestMapping(value = "cart_delete", method = RequestMethod.POST)
	public String cartDelete(CartVO cVo) {

		cartService.deleteCart(cVo.getCseq());

		return "redirect:cart_list";
	}

	@RequestMapping(value = "cart_delete")
	public String cartDelete(@RequestParam(value = "cseq") int[] cseq, Model model) {

		for (int i = 0; i < cseq.length; i++) {
			cartService.deleteCart(cseq[i]);
		}

		return "redirect:cart_list";
	}

	@RequestMapping(value = "order_insert", method = RequestMethod.POST)
	public String orderInsert(OrderVO oVo, HttpSession session, Model model) {

		String result = "";

		MemberVO user = (MemberVO) session.getAttribute("loginUser");

		if (user != null) {
			oVo.setId(user.getId());

			// 주문 테이블에 주문 내역 저장.
			int oseq = orderService.insertOrder(oVo);
			oVo.setResult("1");
			model.addAttribute("oseq", oseq);

			result = "redirect:order_list";
		} else {
			result = "member/login";
		}

		return result;
	}

	@RequestMapping(value = "order_list", method = RequestMethod.GET)
	public String orderList(@RequestParam(value = "oseq") int oseq, Model model, HttpSession session) {

		String result = "";
		MemberVO user = (MemberVO) session.getAttribute("loginUser");

		if (user != null) {
			OrderVO oVo = new OrderVO();
			oVo.setId(user.getId());
			oVo.setOseq(oseq);
			oVo.setResult("1");

			List<OrderVO> orderList = orderService.listOrderById(oVo);

			int total = 0;

			for (OrderVO orderVO : orderList) {
				total += orderVO.getPrice2();
			}

			model.addAttribute("orderList", orderList);
			model.addAttribute("totalPrice", total);

			result = "mypage/orderList";

		} else {

			result = "member/login";
		}

		return result;
	}

	@RequestMapping(value = "mypage", method = RequestMethod.GET)
	public String myPage(HttpSession session, Model model, OrderVO oVo) {

		MemberVO user = (MemberVO) session.getAttribute("loginUser");

		String result = "";

		if (user != null) {

			List<Integer> oseqList = orderService.selectSeqOrdering(user.getId());

			// 사용자의 전체 주문 내역 요약
			List<OrderVO> orderList = new ArrayList<OrderVO>();

			for (int oseq : oseqList) {

				oVo.setId(user.getId());
				oVo.setOseq(oseq);
				oVo.setResult("1");

				// 주문 번호별 주문 내역 조회
				List<OrderVO> listByseq = orderService.listOrderById(oVo);

				// 각 주문 별 내용 축약
				OrderVO vo = new OrderVO();
				vo.setIndate(listByseq.get(0).getIndate());
				vo.setOseq(listByseq.get(0).getOseq());

				if (listByseq.size() > 1) {
					vo.setPname(listByseq.get(0).getPname() + " 외  " + (listByseq.size() - 1) + "건");
				} else {
					vo.setPname(listByseq.get(0).getPname());
				}

				int totalPrice = 0;

				for (int i = 0; i < listByseq.size(); i++) {
					totalPrice += listByseq.get(i).getPrice2() * listByseq.get(i).getQuantity();
				}

				vo.setPrice2(totalPrice);

				orderList.add(vo);

				model.addAttribute("title", "진행 중인 주문 내역");
				model.addAttribute("orderList", orderList);
			}

			result = "mypage/mypage";

		} else {

			result = "member/login";
		}

		return result;
	}

	@RequestMapping(value = "order_detail", method = RequestMethod.GET)
	public String orderDetailView(OrderVO oVo, Model model, HttpSession session) {

		String result = "";

		MemberVO user = (MemberVO) session.getAttribute("loginUser");

		if (user != null) {
			oVo.setId(user.getId());
			oVo.setResult("1");

			List<OrderVO> orderList = orderService.listOrderById(oVo);

			// 주문자 정보 조립
			int totalPrice = 0;
			for (int i = 0; i < orderList.size(); i++) {
				totalPrice += orderList.get(i).getPrice2() * orderList.get(i).getQuantity();
			}

			model.addAttribute("orderList", orderList);
			model.addAttribute("orderDetail", orderList.get(0));
			model.addAttribute("totalPrice", totalPrice);

			result = "mypage/orderDetail";
		} else {
			result = "member/login";
		}

		return result;
	}

	@RequestMapping("order_all")
	public String orderALLView(OrderVO oVo, Model model, HttpSession session) {
		MemberVO user = (MemberVO) session.getAttribute("loginUser");

		String result = "";

		if (user != null) {

			List<Integer> oseqList = orderService.selectSeqOrdering(user.getId());

			// 사용자의 전체 주문 내역 요약
			List<OrderVO> orderList = new ArrayList<OrderVO>();

			for (int oseq : oseqList) {

				oVo.setId(user.getId());
				oVo.setOseq(oseq);
				oVo.setResult("1");

				// 주문 번호별 주문 내역 조회
				List<OrderVO> listByseq = orderService.listOrderById(oVo);

				// 각 주문 별 내용 축약
				OrderVO vo = new OrderVO();
				vo.setIndate(listByseq.get(0).getIndate());
				vo.setOseq(listByseq.get(0).getOseq());

				if (listByseq.size() > 1) {
					vo.setPname(listByseq.get(0).getPname() + " 외  " + (listByseq.size() - 1) + "건");
				} else {
					vo.setPname(listByseq.get(0).getPname());
				}

				int totalPrice = 0;

				for (int i = 0; i < listByseq.size(); i++) {
					totalPrice += listByseq.get(i).getPrice2() * listByseq.get(i).getQuantity();
				}

				vo.setPrice2(totalPrice);

				orderList.add(vo);

				model.addAttribute("title", "총 주문 내역");
				model.addAttribute("orderList", orderList);
			}

			result = "mypage/mypage";

		} else {

			result = "member/login";
		}

		return result;
	}
}
