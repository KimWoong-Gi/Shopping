package com.green.biz.member.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.member.MemberService;
import com.green.biz.member.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDao;

	@Override
	public MemberVO getMember(MemberVO mVo) {

		return memberDao.getMember(mVo.getId());
	}

	@Override
	public MemberVO loginMember(MemberVO mVo) {
		
		return memberDao.loginMember(mVo);
	}

	@Override
	public void insertMember(MemberVO mVo) {

		memberDao.insertMember(mVo);
	}

	@Override
	public MemberVO getMemberByNameAndEmail(MemberVO mVo) {
		
		return memberDao.getMemberByNameAndEmail(mVo);
	}

	@Override
	public MemberVO getMemberByIdAndNameAndEmail(MemberVO mVo) {
		
		return memberDao.getMemberByIdAndNameAndEmail(mVo);
	}

	@Override
	public List<MemberVO> listMember(MemberVO mVo) {
		
		return memberDao.listMember(mVo);
	}

}
