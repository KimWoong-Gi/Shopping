package com.green.biz.worker.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.employee.EmployeeVO;

@Repository
public class WorkerDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public String workerCheck(String id) {
		
		return mybatis.selectOne("WorkerDAO.workerCheck", id);
	}
	
	public EmployeeVO getEmployee(String id) {
		
		return mybatis.selectOne("WorkerDAO.getEmployee", id);
	}
	
}
