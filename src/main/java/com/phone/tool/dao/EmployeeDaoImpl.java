package com.phone.tool.dao;

import java.util.List;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import com.homeshope.item.Item;

import phone.tool.pojo.Employee;

public class EmployeeDaoImpl implements EmployeeDao {

	DBI dbi;

	public EmployeeDaoImpl(DBI dbi) {
		this.dbi = dbi;
	}

	public List<Employee> getAllItems() {
		StringBuilder queryBuilder = new StringBuilder();
		   queryBuilder.append(
		       "WITH RECURSIVE subordinates AS (SELECT employee_id,parent_id,employee_name FROM employee WHERE employee_id = 1000 UNION SELECT e.employee_id, e.parent_id, e.employee_name FROM employee e INNER JOIN subordinates s ON s.employee_id = e.parent_id) SELECT employee_name as name,parent_id as parentId,employee_id as employeeId  FROM subordinates;");
		  // LOG.debug("fetchStatusList - Query is <{}>", queryBuilder.toString());
		   try (Handle handle = dbi.open()) {
		     return handle.createQuery(queryBuilder.toString())
					.map(Employee.class).list();
		   }

	}

	public Item getItem(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
