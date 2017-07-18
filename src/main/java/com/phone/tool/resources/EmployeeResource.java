package com.phone.tool.resources;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.phone.tool.dao.EmployeeDao;
import com.phone.tool.dao.EmployeeDaoImpl;

import phone.tool.pojo.Employee;
import phone.tool.util.ResponseUtil;
import phone.tool.util.TreeUtil;

@Path("phonetool")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

	EmployeeDao employeeDao;

	public EmployeeResource(DBI dbi) {
		employeeDao = new EmployeeDaoImpl(dbi);
	}

	@Path("subtree/{employeeId}")
	@GET
	public Response getSubtree(@PathParam("employeeId") int employeeId) {

		// check for employeeId mustbe greater than 0

		List<Employee> data = employeeDao.getSubTree(employeeId, true);

		if (data != null && !data.isEmpty())
			return Response.ok(ResponseUtil.getApiResponse(data, 200, true)).build();
		else
			return Response.ok(ResponseUtil.getApiResponse(" record not found", 400, false)).build();
	}

	@Path("tree/{employeeId}")
	@GET
	public Response getTree(@PathParam("employeeId") int employeeId) {
		List<Employee> data = employeeDao.getTree(employeeId);

		return Response.ok(ResponseUtil.getApiResponse(data, 200, true)).build();
	}

	@Path("subtree/joindate/{employeeId}")
	@GET
	public Response getSubtreeWithGreaterJoinDate(@PathParam("employeeId") int employeeId) {
		List<Employee> data = employeeDao.getSubTree(employeeId, false);
		return Response.ok(ResponseUtil.getApiResponse(data, 200, true)).build();
	}

	@Path("subtree/shortestpath/{employeeId1}/{employeeId2}")
	@GET
	public Response getShortestPath(@PathParam("employeeId1") int employeeId1,
			@PathParam("employeeId2") int employeeId2) {
		List<Employee> empList1 = employeeDao.getShortestPath(employeeId1);
		List<Employee> empList2 = employeeDao.getShortestPath(employeeId2);
		return Response.ok(ResponseUtil
				.getApiResponse(TreeUtil.getShortestPath(empList1, empList2, employeeId1, employeeId2), 200, true))
				.build();
	}

	@POST
	@Path("createtree")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEmployee(@NotNull List<Employee> employees) {
		if (!employees.isEmpty())
			for (Employee emp : employees) {
				if (employeeDao.getEmployee(emp.getEmployeeId()) == null) {
					employeeDao.createEmployee(emp);
				} else {
					employeeDao.updateEmployee(emp);
				}
			}
		return Response.ok(ResponseUtil.getApiResponse("record saved", 200, true)).build();
	}

}
