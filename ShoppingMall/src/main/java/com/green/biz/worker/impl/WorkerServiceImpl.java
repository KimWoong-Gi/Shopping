package com.green.biz.worker.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.employee.EmployeeService;
import com.green.biz.employee.EmployeeVO;

@Service("workerService")
public class WorkerServiceImpl implements EmployeeService {

	@Autowired
	private WorkerDAO wDao;
	
	@Override
	public int workerCheck(String id, String pwd) {
		
		String checkPwd = wDao.workerCheck(id);
		
		if(checkPwd == null) {
			return -1;
		}else if (checkPwd.equals(pwd)) {
			return 1;
		}else {
			return 0;
		}
	}

	@Override
	public EmployeeVO getEmployee(String id) {
		
		return wDao.getEmployee(id);
	}

}
