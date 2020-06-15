package com.green.view.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.biz.member.MemberVO;
import com.green.biz.qna.QnaService;
import com.green.biz.qna.QnaVO;

@Controller
public class QnaController {

	@Autowired
	private QnaService qnaService;

	@RequestMapping(value = "qna_list")
	public String listQna(Model model, HttpSession session) {

		String result = "";

		MemberVO user = (MemberVO) session.getAttribute("loginUser");

		if (user != null) {
			List<QnaVO> qnaList = qnaService.listQna(user.getId());

			model.addAttribute("qnaList", qnaList);

			result = "qna/qnaList";
		} else {
			result = "member/login";
		}

		return result;
	}

	@RequestMapping(value = "qna_view")
	public String getQna(@RequestParam(value = "qseq") int qseq, Model model, HttpSession session) {
		String result = "";

		MemberVO user = (MemberVO) session.getAttribute("loginUser");

		if (user != null) {
			QnaVO qVo = qnaService.getQna(qseq);

			model.addAttribute("qnaVO", qVo);

			result = "qna/qnaView";
		} else {
			result = "member/login";
		}

		return result;

	}

	@RequestMapping(value = "qna_write_form")
	public String qnaWriteView(HttpSession session, Model model) {
		String result = "";

		MemberVO user = (MemberVO) session.getAttribute("loginUser");

		if (user != null) {
			result = "qna/qnaWrite";
		} else {
			result = "member/login";
		}

		return result;
	}

	@RequestMapping(value = "qna_write")
	public String qnaWrite(HttpSession session, Model model, QnaVO qVo) {
		String result = "";

		MemberVO user = (MemberVO) session.getAttribute("loginUser");

		if (user != null) {
			qVo.setId(user.getId());
			
			qnaService.insertQna(qVo);

			result = "redirect:qna_list";
		} else {
			result = "member/login";
		}

		return result;
	}

}
