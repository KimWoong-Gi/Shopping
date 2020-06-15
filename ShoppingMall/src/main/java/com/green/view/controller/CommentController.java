package com.green.view.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.green.biz.member.MemberVO;
import com.green.biz.product.CommentVO;
import com.green.biz.product.ProductService;

@RestController // �Ϲ������� ȭ���� �������ִ� Controller�ʹ� �޸� ȭ���� �ƴ� ��� �����͸� �������ش�.
public class CommentController {
	
	@Autowired
	private ProductService ps;
	
	@RequestMapping(value="save_comment", method=RequestMethod.POST)
	@ResponseBody
	public String insertComment(CommentVO cVo, HttpSession session) {
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		if(loginUser != null) {
			
			String userId = loginUser.getId();
			
			cVo.setWriter(userId);
			
			ps.insertComment(cVo);
			
			return ("success");
		}else {
			return("fail");
		}
		
	}
	
	@RequestMapping(value="comment_list", method=RequestMethod.GET)
	public List<CommentVO> getCommentList(CommentVO cVo){
		int pseq= cVo.getPseq();
		System.out.println("��ǰ��ȣ=" + pseq);
		
		List<CommentVO> commentList = ps.getCommentList(pseq);
		
		return commentList;
	}
}
