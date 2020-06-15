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
	
	// id ���� Ȯ��
	public MemberVO getMember(String id) {
		System.out.println("===> Mybatis�� getMember() ��� ó��");

		return mybatis.selectOne("MemberDAO.getMember", id);
	}
	
	// id, pwd Ȯ��
	public MemberVO loginMember(MemberVO mVo) {
		System.out.println("===> Mybatis�� loginMember() ��� ó��");

		return mybatis.selectOne("MemberDAO.loginMember", mVo);
	}
	
	// ȸ�� ����
	public void insertMember(MemberVO mVo) {
		System.out.println("===> Mybatis�� insertMember() ��� ó��");

		mybatis.insert("MemberDAO.insertMember", mVo);
	}
	
	// ���̵� ã��
	public MemberVO getMemberByNameAndEmail(MemberVO mVo) {
		System.out.println("===> Mybatis�� getMemberByNameAndEmail() ��� ó��");

		return mybatis.selectOne("MemberDAO.getMemberByNameAndEmail", mVo);
	}
	
	// ��й�ȣ ã��
	public MemberVO getMemberByIdAndNameAndEmail(MemberVO mVo) {
		System.out.println("===> Mybatis�� getMemberByIdAndNameAndEmail() ��� ó��");

		return mybatis.selectOne("MemberDAO.getMemberByIdAndNameAndEmail", mVo);
	}
	
	public List<MemberVO> listMember(MemberVO mVo){
		
		return mybatis.selectList("MemberDAO.listMember", mVo);
	}

}
