package com.green.biz.employee;

public interface EmployeeService {
	int workerCheck(String id, String pwd);
	EmployeeVO getEmployee(String id);
}