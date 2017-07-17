package com.phone.tool.dao;

import java.sql.Date;
import java.util.List;

import com.homeshope.item.Item;

import phone.tool.pojo.Employee;

public interface EmployeeDao {
	
	public List<Employee> getSubTree(Integer employeeId ,boolean isJoinDate);
	
	public List<Employee> getTree(Integer employeeId);
		
	public Employee setEmployee(Employee employee);
	

}
