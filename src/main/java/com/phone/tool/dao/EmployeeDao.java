package com.phone.tool.dao;

import java.util.List;

import phone.tool.pojo.Employee;

public interface EmployeeDao {

	public List<Employee> getSubTree(Integer employeeId, boolean isJoinDate);

	public List<Employee> getTree(Integer employeeId);

	public List<Employee> setEmployee(List<Employee> employee);

	public List<Employee> getShortestPath(Integer emp1, Integer emp2);
}
