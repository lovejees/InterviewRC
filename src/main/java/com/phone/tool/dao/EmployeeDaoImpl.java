package com.phone.tool.dao;

import java.sql.Date;
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

	public List<Employee> getSubTree(Integer employeeId ,boolean isJoinDate) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("WITH RECURSIVE subordinates AS (SELECT employee_id,parent_id,employee_name FROM employee WHERE employee_id = "+employeeId+" UNION SELECT e.employee_id, e.parent_id, e.employee_name FROM employee e INNER JOIN subordinates s ON s.employee_id = e.parent_id) SELECT employee_name as name,parent_id as parentId,employee_id as employeeId  ");
		   if(isJoinDate){queryBuilder.append(
		       " FROM subordinates where employee_id <> "+employeeId+";");
		   }else
		   {
			   queryBuilder.append("FROM subordinates where join_date > (select join_date from employee where employee_id = "+employeeId+");");
		   }
		   try (Handle handle = dbi.open()) {
		     return handle.createQuery(queryBuilder.toString())
					.map(Employee.class).list();
		   }

	}

	@Override
	public List<Employee> getTree(Integer employeeId) {
		StringBuilder queryBuilder = new StringBuilder();
		   queryBuilder.append(
		       "WITH RECURSIVE subordinates AS (SELECT employee_id,parent_id,employee_name FROM employee WHERE employee_id = "+employeeId+" UNION SELECT e.employee_id, e.parent_id, e.employee_name FROM employee e INNER JOIN subordinates s ON s.employee_id = e.parent_id) SELECT employee_name as name,parent_id as parentId,employee_id as employeeId  FROM subordinates;");
		   try (Handle handle = dbi.open()) {
		     return handle.createQuery(queryBuilder.toString())
					.map(Employee.class).list();
		   }
	}

	@Override
	public Employee setEmployee(Employee emp) {
	    StringBuilder queryBuilder = new StringBuilder();
	    queryBuilder.append(" insert into employee (id,name,parent_id,is_active,age,join_date) ")
	        .append(" values(nextval('employee_id_seq'),:name,:parentId,:isActive,:age,:joinDate) ")
	        .append(" returning id ");
	    try (Handle handle = dbi.open()) {
	      return handle.createQuery(queryBuilder.toString()).bind("name", emp.getName()).bind("parentId", emp.getParentId())
	         .bind("isActive", emp.isActive()).bind("age", emp.getAge()).bind("joinDate", emp.getJoinDate())
	          .map(Employee.class).first();
	    }
	  }
	


}
