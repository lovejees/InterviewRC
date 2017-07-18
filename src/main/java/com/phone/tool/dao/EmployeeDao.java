package com.phone.tool.dao;

import java.util.List;

import phone.tool.pojo.Employee;

public interface EmployeeDao {

	public List<Employee> getSubTree(Integer employeeId, boolean isJoinDate);

	public List<Employee> getTree(Integer employeeId);

	public Employee createEmployee(Employee emp);

	public List<Employee> getShortestPath(Integer emp);
	
	public Integer updateEmployee(Employee emp);
	
	public Employee getEmployee(Integer employeeId);
}
