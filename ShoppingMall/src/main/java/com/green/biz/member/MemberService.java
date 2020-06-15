package com.green.biz.member;

import java.util.List;

public interface MemberService {
	
	MemberVO getMember(MemberVO mVo);
	
	MemberVO loginMember(MemberVO mVo);
	
	void insertMember(MemberVO mVo);
	
	MemberVO getMemberByNameAndEmail(MemberVO mVo);
	
	MemberVO getMemberByIdAndNameAndEmail(MemberVO mVo);
	
	List<MemberVO> listMember(MemberVO mVo);
}
