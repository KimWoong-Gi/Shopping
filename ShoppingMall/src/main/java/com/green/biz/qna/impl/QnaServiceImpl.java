package com.green.biz.qna.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.qna.QnaService;
import com.green.biz.qna.QnaVO;

@Service("qnaService")
public class QnaServiceImpl implements QnaService {
	
	@Autowired
	private QnaDAO qDao;
	
	@Override
	public List<QnaVO> listQna(String id) {
		
		return qDao.listQna(id);
	}

	@Override
	public QnaVO getQna(int qseq) {
		
		return qDao.getQna(qseq);
	}

	@Override
	public void insertQna(QnaVO qVo) {
		
		qDao.insertQna(qVo);
	}

	@Override
	public List<QnaVO> listAllQna() {
		
		return qDao.listAllQna();
	}

	@Override
	public void updateQna(QnaVO qVo) {
		
		qDao.updateQna(qVo);
	}

}
