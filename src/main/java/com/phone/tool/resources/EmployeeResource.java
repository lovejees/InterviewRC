package com.phone.tool.resources;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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

import API.Sample.ApiResponse;
import phone.tool.pojo.Employee;
import phone.tool.util.ResponseUtil;

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
		List<Employee> data = employeeDao.getSubTree(employeeId, true);

		return Response.ok(ResponseUtil.getApiResponse(data, 200, true)).build();
	}

	@Path("tree/{employeeId}")
	@GET
	public Response getTree(@PathParam("employeeId") int employeeId) {
		List<Employee> data = employeeDao.getTree(employeeId);

		return Response.ok(ResponseUtil.getApiResponse(data, 200, true)).build();
	}	


	@Path("subtree/joinDate/{employeeId}")
	@GET
	public Response getSubtreeWithGreaterJoinDate(@PathParam("employeeId") int employeeId) {
		List<Employee> data = employeeDao.getSubTree(employeeId, false);

		return Response.ok(ResponseUtil.getApiResponse(data, 200, true)).build();
	}
	

	@Path("subtree/shortestpath/{employeeId}")
	@GET
	public Response getShortestPath(@PathParam("employeeId") int employeeId) {
		List<Employee> data = employeeDao.getSubTree(employeeId, false);

		return Response.ok(ResponseUtil.getApiResponse(data, 200, true)).build();
	}
	
	@POST
	@Path("createtree")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEmployee(List<Employee> employee) {
		ApiResponse response = null;
		List<Employee> emp = employeeDao.setEmployee(employee);
		return Response.ok(response).build();
	}
	
	
}
