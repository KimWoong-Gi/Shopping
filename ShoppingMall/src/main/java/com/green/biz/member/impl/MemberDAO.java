package com.green.biz.member.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.member.MemberVO;

@Repository
public class MemberDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	// id 존재 확인
	public MemberVO getMember(String id) {
		System.out.println("===> Mybatis로 getMember() 기능 처리");

		return mybatis.selectOne("MemberDAO.getMember", id);
	}
	
	// id, pwd 확인
	public MemberVO loginMember(MemberVO mVo) {
		System.out.println("===> Mybatis로 loginMember() 기능 처리");

		return mybatis.selectOne("MemberDAO.loginMember", mVo);
	}
	
	// 회원 가입
	public void insertMember(MemberVO mVo) {
		System.out.println("===> Mybatis로 insertMember() 기능 처리");

		mybatis.insert("MemberDAO.insertMember", mVo);
	}
	
	// 아이디 찾기
	public MemberVO getMemberByNameAndEmail(MemberVO mVo) {
		System.out.println("===> Mybatis로 getMemberByNameAndEmail() 기능 처리");

		return mybatis.selectOne("MemberDAO.getMemberByNameAndEmail", mVo);
	}
	
	// 비밀번호 찾기
	public MemberVO getMemberByIdAndNameAndEmail(MemberVO mVo) {
		System.out.println("===> Mybatis로 getMemberByIdAndNameAndEmail() 기능 처리");

		return mybatis.selectOne("MemberDAO.getMemberByIdAndNameAndEmail", mVo);
	}
	
	public List<MemberVO> listMember(MemberVO mVo){
		
		return mybatis.selectList("MemberDAO.listMember", mVo);
	}

}
