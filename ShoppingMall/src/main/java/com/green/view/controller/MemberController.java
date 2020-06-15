package com.green.view.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.green.biz.address.AddressService;
import com.green.biz.address.AddressVO;
import com.green.biz.member.MemberService;
import com.green.biz.member.MemberVO;

@Controller
@SessionAttributes("loginUser")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private AddressService addressService;
	
	@RequestMapping(value = "index")
	public String home() {
		return "user/loginForm";
	}
	
	// 약관 동의 페이지 이동 메소드
	@RequestMapping(value="/contract", method=RequestMethod.GET)
	public String contractView() {
		
		return "member/contract";
	}
	
	// 회원가입 화면 출력 메소드
	@RequestMapping(value="/join_form", method=RequestMethod.GET)
	public String joinView() {
		
		return "member/join";
	}
	
	// 회원가입 화면 출력 메소드
	@RequestMapping(value="/join_form", method=RequestMethod.POST)
	public String joinForm() {
		
		return "member/join";
	}
	
	// 아이디 중복 체크 화면 출력 메소드
	@RequestMapping(value="/id_check_form", method=RequestMethod.GET)
	public String idCheckView(@RequestParam(value="id", defaultValue="", required=false) String id, Model model) {
		model.addAttribute("id", id);
		
		return "member/idcheck";
	}
	
	// 화면의 id를 받아 데이터베이스에서 id의 존재 여부 확인, 사용자가 존재할 때 message = 1, 없으면 -1
	@RequestMapping(value="/id_check_form", method=RequestMethod.POST)
	public String idCheckAction(@RequestParam(value="id", defaultValue="", required=false) String id, Model model) {	
		MemberVO mVo = new MemberVO();
		mVo.setId(id);
		
		MemberVO user = memberService.getMember(mVo);
		
		if (user != null) { // 아이디 중복
			model.addAttribute("message", 1);
		} else {	// 아이디 사용 가능, 중복 x
			model.addAttribute("message", -1);
		}
		
		model.addAttribute("id", id);
		
		return "member/idcheck";
	}
	
	// 아이디 중복 체크
	@RequestMapping(value="/id_check_confirm", method=RequestMethod.GET)
	public String idCheckConfirm(MemberVO mVo, Model model) {
		
		model.addAttribute("reid", mVo.getId());
		
		return "member/join";
	}
	
	// 우편번호 조회창을 표시
	@RequestMapping(value="/find_zip_num", method=RequestMethod.GET)
	public String findZipNum(String zip_num, Model model) {
		
		model.addAttribute("zip_num", zip_num);
		
		return "member/findZipNum";
	}
	
	// 동이름으로 우편번호 검색
	@RequestMapping(value="/find_zip_num", method=RequestMethod.POST)
	public String findZipNumAction(AddressVO aVo, Model model) {
				
		List<AddressVO> list = addressService.selectAddressByDong(aVo);
		
		model.addAttribute("addressList", list);
		
		return "member/findZipNum";
	}
	
	// 회원 가입
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String insertMember(@RequestParam(value="addr1") String addr1, @RequestParam(value="addr2") String addr2, MemberVO mVo) {
		
		mVo.setAddress(addr1 +" "+ addr2);
		
		memberService.insertMember(mVo);
		
		return "member/login";
	}
	
	// 로그인 화면 이동
	@RequestMapping(value="/login_form", method=RequestMethod.GET)
	public String loginForm() {
		return "member/login";
	}
	
	// 로그인 액션
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginAction(MemberVO mVo, Model model) {
		String result ="";
		
		// 데이터베이스에서 사용자 존재 확인
		MemberVO user = memberService.loginMember(mVo);
		
		if(user != null) {
			if(!user.getPwd().equals(mVo.getPwd())) {
				result = "member/password_fail";
			}else {
			model.addAttribute("loginUser", user);
			result = "redirect:/index";
			}
		}else {
			result = "member/login_fail";
		}
		return result;
	}
	
	// 로그아웃
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logoutAction(SessionStatus session) {
		
		session.setComplete();
		
		return "redirect:/login_form";
	}
	
	// 아이디, 비밀번호 찾기 화면 이동
	@RequestMapping(value="/find_id_form", method=RequestMethod.GET)
	public String findIdAndPwdForm() {
		
		return "member/findIdAndPassword";
	}
	
	// 아이디 찾기
	@RequestMapping(value="/find_id", method=RequestMethod.GET)
	public String findIdAction(MemberVO mVo, Model model) {
		
		MemberVO vo = memberService.getMemberByNameAndEmail(mVo);
		
		if(vo != null) {
			model.addAttribute("id",vo.getId());
			model.addAttribute("message", 1);
		}else {
			model.addAttribute("message", -1);
		}
		
		
		return "member/findResult";
	}
	
	// 비밀번호 찾기
	@RequestMapping(value="/find_pwd", method=RequestMethod.GET)
	public String findPwdAction(MemberVO mVo, Model model) {
		
		MemberVO vo = memberService.getMemberByIdAndNameAndEmail(mVo);
		
		if(vo != null) {
			model.addAttribute("pwd",vo.getPwd());
			model.addAttribute("id",mVo.getId());
			model.addAttribute("message", 1);
		}else {
			model.addAttribute("message", -1);
		}
		
		
		return "member/findPwdResult";
	}
}
