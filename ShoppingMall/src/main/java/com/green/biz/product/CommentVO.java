package com.green.biz.product;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentVO {
	
	private int comment_seq;
	private int pseq;
	private String content;
	private String writer;
	private Timestamp regdate;
	private Timestamp modifydate;
}
