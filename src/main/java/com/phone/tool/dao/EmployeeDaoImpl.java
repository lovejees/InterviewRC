package com.phone.tool.dao;

import java.util.List;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import phone.tool.pojo.Employee;

public class EmployeeDaoImpl implements EmployeeDao {

	DBI dbi;

	public EmployeeDaoImpl(DBI dbi) {
		this.dbi = dbi;
	}

	public List<Employee> getSubTree(Integer employeeId, boolean isJoinDate) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder
				.append("WITH RECURSIVE subordinates AS (SELECT employee_id,parent_id,employee_name,join_date FROM employee WHERE employee_id = "
						+ employeeId
						+ " UNION SELECT e.employee_id, e.parent_id, e.employee_name,e.join_date FROM employee e INNER JOIN subordinates s ON s.employee_id = e.parent_id) SELECT employee_name as name,parent_id as parentId,employee_id as employeeId,join_date as joinDate ");
		if (isJoinDate) {
			queryBuilder.append(" FROM subordinates where employee_id <> " + employeeId + ";");
		} else {
			queryBuilder
					.append("FROM subordinates where join_date > (select join_date from employee where employee_id = "
							+ employeeId + ");");
		}
		try (Handle handle = dbi.open()) {
			return handle.createQuery(queryBuilder.toString()).map(Employee.class).list();
		}

	}

	@Override
	public List<Employee> getTree(Integer employeeId) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder
				.append("WITH RECURSIVE subordinates AS (SELECT employee_id,parent_id,employee_name,join_date FROM employee WHERE employee_id = "
						+ employeeId
						+ " UNION SELECT e.employee_id, e.parent_id, e.employee_name,e.join_date FROM employee e INNER JOIN subordinates s ON s.employee_id = e.parent_id) SELECT employee_name as name,parent_id as parentId,employee_id as employeeId,join_date as joinDate  FROM subordinates;");
		try (Handle handle = dbi.open()) {
			return handle.createQuery(queryBuilder.toString()).map(Employee.class).list();
		}
	}

	@Override
	public Employee createEmployee(Employee emp) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" insert into employee (employee_id,employee_name,parent_id,join_date) ")
				.append(" values(nextval('employee_id_sequence'),:employee_name,:parentId,:joinDate) ")
				.append(" returning employee_id ");
		try (Handle handle = dbi.open()) {
			return handle.createQuery(queryBuilder.toString()).bind("employee_name", emp.getName())
					.bind("parentId", emp.getParentId()).bind("joinDate", emp.getJoinDate()).map(Employee.class)
					.first();
		}
	}

	@Override
	public List<Employee> getAncestorPath(Integer emp) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder
				.append("WITH RECURSIVE subordinates AS (SELECT employee_id,parent_id,employee_name,join_date FROM employee WHERE employee_id = "
						+ emp
						+ " UNION SELECT e.employee_id, e.parent_id, e.employee_name,e.join_date FROM employee e INNER JOIN subordinates s ON s.parent_id = e.employee_id) SELECT employee_name as name,parent_id as parentId,employee_id as employeeId,join_date as joinDate FROM subordinates where employee_id <> "
						+ emp + ";");
		try (Handle handle = dbi.open()) {
			return handle.createQuery(queryBuilder.toString()).map(Employee.class).list();
		}
	}

	public Integer updateEmployee(Employee emp) {

		StringBuilder queryBuilder = new StringBuilder();

		queryBuilder

				.append("UPDATE employee set employee_name = :name ,parent_id=:parentId")

				.append(" WHERE employee_id=:id ;");

		try (Handle handle = dbi.open()) {

			return handle.createStatement(queryBuilder.toString()).bind("id", emp.getEmployeeId())

					.bind("name", emp.getName()).bind("parentId", emp.getParentId()).execute();

		}

	}

	@Override
	public Employee getEmployee(Integer employeeId) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select * from employee where employee_id = " + employeeId + ";");
		try (Handle handle = dbi.open()) {
			return handle.createQuery(queryBuilder.toString()).map(Employee.class).first();
		}

	}

	@Override
	public List<Employee> getAll() {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				"select employee_name as name,parent_id as parentId,employee_id as employeeId,join_date as joinDate from employee;");
		try (Handle handle = dbi.open()) {
			return handle.createQuery(queryBuilder.toString()).map(Employee.class).list();
		}
	}

}
