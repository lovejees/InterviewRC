package com.phone.tool.dao;

import java.util.List;

import com.homeshope.item.Item;

import phone.tool.pojo.Employee;

public interface EmployeeDao {
	
	public List<Employee> getAllItems();
	
	public Item getItem(Integer id);

}
