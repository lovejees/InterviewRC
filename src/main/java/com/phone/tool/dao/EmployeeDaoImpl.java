package com.phone.tool.dao;

import java.util.ArrayList;
import java.util.List;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

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
	public List<Employee> setEmployee(List<Employee> emplist) {
	    StringBuilder queryBuilder = new StringBuilder();
	    List<Employee> returnList = new ArrayList<Employee>();
	    for(Employee emp : emplist){
	    queryBuilder.append(" insert into employee (employee_id,employee_name,parent_id,is_active,age,join_date) ")
	        .append(" values(nextval('employee_id_sequence'),:employee_name,:parentId,:isActive,:age,:joinDate) ")
	        .append(" returning employee_id ");
	    try (Handle handle = dbi.open()) {
	       returnList.add(handle.createQuery(queryBuilder.toString()).bind("employee_name", emp.getName()).bind("parentId", emp.getParentId())
	         .bind("isActive", emp.isActive()).bind("age", emp.getAge()).bind("joinDate", emp.getJoinDate())
	          .map(Employee.class).first());
	    }
	  }return returnList;
	  }

	@Override
	public List<Employee> getShortestPath(Integer emp1, Integer emp2) {
		List<Employee> list1,list2;
		StringBuilder queryBuilder = new StringBuilder();
		   queryBuilder.append(
		       "WITH RECURSIVE subordinates AS (SELECT employee_id,parent_id,employee_name FROM employee WHERE employee_id = "+emp1+" UNION SELECT e.employee_id, e.parent_id, e.employee_name FROM employee e INNER JOIN subordinates s ON s.parent_id = e.employee_id) SELECT employee_name as name,parent_id as parentId,employee_id as employeeId FROM subordinates;;");
		   try (Handle handle = dbi.open()) {
		     list1 = handle.createQuery(queryBuilder.toString())
					.map(Employee.class).list();
		   }
		   
		   StringBuilder queryBuilder2 = new StringBuilder();
		   queryBuilder2.append(
		       "WITH RECURSIVE subordinates AS (SELECT employee_id,parent_id,employee_name FROM employee WHERE employee_id = "+emp2+" UNION SELECT e.employee_id, e.parent_id, e.employee_name FROM employee e INNER JOIN subordinates s ON s.parent_id = e.employee_id) SELECT employee_name as name,parent_id as parentId,employee_id as employeeId FROM subordinates;;");
		   try (Handle handle = dbi.open()) {
		     list2 = handle.createQuery(queryBuilder.toString())
					.map(Employee.class).list();
		   }
		   
		return getShortestPath(list1,list2);
	}

	private List<Employee> getShortestPath(List<Employee> list1, List<Employee> list2) {
		return null;
	}
	
	


}
