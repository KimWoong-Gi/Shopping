package com.green.biz.qna.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.qna.QnaVO;

@Repository
public class QnaDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<QnaVO> listQna(String id){
		
		return mybatis.selectList("QnaDAO.listQna", id);
	}
	
	public QnaVO getQna(int qseq) {
		
		return mybatis.selectOne("QnaDAO.getQna", qseq);
	}
	
	public void insertQna(QnaVO qVo) {
		
		mybatis.insert("QnaDAO.insertQna", qVo);
	}
	
	public List<QnaVO> listAllQna(){
		
		return mybatis.selectList("QnaDAO.listAllQna");
	}
	
	public void updateQna(QnaVO qVo) {
		
		mybatis.update("QnaDAO.updateQna", qVo);
	}
}
