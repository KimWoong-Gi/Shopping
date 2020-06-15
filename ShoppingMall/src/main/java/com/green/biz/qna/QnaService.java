package com.green.biz.qna;

import java.util.List;

public interface QnaService {

	List<QnaVO> listQna(String id);

	QnaVO getQna(int qseq);

	void insertQna(QnaVO qVo);
	
	List<QnaVO> listAllQna();
	
	void updateQna(QnaVO qVo);
}