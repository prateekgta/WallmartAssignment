package com.wallmart.web.webservices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wallmart.web.model.TaskBean;
import com.wallmart.web.model.UserBean;
import com.wallmart.web.services.UserServices;

/*
 * Class Used by WebServices 
 * 
 */

@Component
@Path("task")
public class TaskWebService {

	@Autowired
	public UserServices userService;
	SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/*
	 * Method to Get List of valid user task
	 */
	@GET
	@Path("/getTaskList/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listOfTask(@PathParam("userId") String userId) {
		int intUserId = Integer.valueOf(userId);
		UserBean userObject = userService.getUser(intUserId);
		if (userObject == null) {
			return Response.status(401).entity("User object invalid, Unauthorized").build();
		}
		List<TaskBean> result = userService.getTaskforUser(intUserId);
		return Response.ok(result).build();
	}

	/*
	 * Method to create new task for valid user. We assume here that user will only
	 * give task detail, it's task status is in submit and with low priority in
	 * table
	 */
	@POST
	@Path("/create/{userId}/{task}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response createTask(@PathParam("userId") String userId, @PathParam("task") String task) {
		System.out.println("userId" + userId + "task" + task);
		int intUserId = Integer.valueOf(userId);
		UserBean userObject = userService.getUser(intUserId);
		if (userObject == null) {
			return Response.status(401).entity("User object invalid, Unauthorized").build();
		}
		String date = sdf.format(new Date());
		TaskBean taskObj = new TaskBean(intUserId, intUserId, task, "Submitted", 5, 0, date, date);
		taskObj.setTaskId(userService.addTask(taskObj));
		return Response.status(201).entity(taskObj).build();
	}

}
