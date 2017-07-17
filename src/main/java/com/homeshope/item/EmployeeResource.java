package com.homeshope.item;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.phone.tool.dao.EmployeeDao;
import com.phone.tool.dao.EmployeeDaoImpl;

import API.Sample.ApiResponse;
import phone.tool.pojo.Employee;

@Path("item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {
	
	DBI dbi;
	public EmployeeResource(DBI dbi) {
		this.dbi = dbi;
	}



	@Path("all")
	@GET
	public Response getItem() {
		EmployeeDao itemDao = new EmployeeDaoImpl(dbi );
		ApiResponse apiResponse = new ApiResponse();
		List<Employee> data =  new ArrayList<Employee>();
		data = itemDao.getAllItems();
		System.out.println(data.get(0).getEmployeeId());
		apiResponse.setData(data);
		apiResponse.setStatus(200);
		apiResponse.setSuccess(true);
		return Response.ok(apiResponse).build();
	}

}
