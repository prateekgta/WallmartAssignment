package com.wallmart.web.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.wallmart.web.customexception.ServiceCustomException;
import com.wallmart.web.dao.TaskDao;
import com.wallmart.web.dao.UserDao;
import com.wallmart.web.model.LoginBean;
import com.wallmart.web.model.TaskBean;
import com.wallmart.web.model.UserBean;

public class UserServices {

	UserDao userDao;
	TaskDao taskDao;
	@Autowired
	GetBeanService beanService; 
	

	/**
	 * Service method used for authentication of user.
	 * 
	 * @param login
	 *            object, it cannot be null or empty.
	 * @return Object.
	 */

	public UserBean authenticate(LoginBean login) {
		if (login == null|| StringUtils.isBlank(login.getUserName())
				|| StringUtils.isBlank(login.getPassword())) {
			throw new ServiceCustomException("login credential not valid");
		}
		return ((UserDao) beanService.getBean("userDao")).validateUser(login);
	}

	/**
	 * Service method used to delete task.
	 * 
	 * @param taskId,
	 *            taskId of the task for deletion .
	 */

	public void deleteTask(int taskId) {
		((TaskDao) beanService.getBean("taskDao")).deleteTask(taskId);
	}

	/**
	 * Service method to register new user.
	 * 
	 * @param userbean
	 *            object, user cannot be null or empty.
	 */

	public void registration(UserBean user) {
		if (user == null)
			return;
		((UserDao) beanService.getBean("userDao")).registerUser(user);
	}

	/**
	 * Service method to add a new task.
	 * 
	 * @param taskbean
	 *            object, user taskBean be null or empty.
	 * @return if object is null or invalid, return -1 else return new task object
	 *         id.
	 */

	public int addTask(TaskBean task) {
		if (task == null)
			return -1;
		return ((TaskDao) beanService.getBean("taskDao")).addTask(task);
	}

	/**
	 * Service method to get list of user task.
	 * 
	 * @param userId,
	 *            userId cannot be negative.
	 * @return if userId is invalid, return null, otherwise list of task of user.
	 */

	public List<TaskBean> getTaskforUser(int userId) {
		if (userId <= 0)
			return null;
		List<TaskBean> listOfTask = ((TaskDao) beanService.getBean("taskDao")).getTaskforUser(userId);
		getSortedTaskList(listOfTask);
		return listOfTask;
	}

	/**
	 * Service method to get list of task created by user.
	 * 
	 * @param userId,
	 *            userId cannot be negative.
	 * @return if userId is invalid, return null, otherwise list of task created by
	 *         user.
	 */

	public List<TaskBean> getTaskCreatedByAdmin(int userId) {
		if (userId <= 0)
			return null;
		List<TaskBean> listOfTask = ((TaskDao) beanService.getBean("taskDao")).getTaskCreatedByUser(userId);
		getSortedTaskList(listOfTask);
		return listOfTask;
	}

	/**
	 * Service method to update task.
	 * 
	 * @param taskBean
	 *            object, taskBean cannot be null.
	 */

	public void updateTask(TaskBean updatedTaskObject) {
		if (updatedTaskObject == null)
			return;
		((TaskDao) beanService.getBean("taskDao")).updateTaskForUser(updatedTaskObject);
	}

	/**
	 * Service method to get task object.
	 * 
	 * @param taskId,
	 *            taskId cannot be negative.
	 */

	public TaskBean getTaskObject(int taskId) {
		if (taskId <= 0)
			return null;
		return ((TaskDao) beanService.getBean("taskDao")).getTaskObjectForUser(taskId);
	}

	/**
	 * Service methods to get all users of application.
	 * 
	 * @return return the Map object with userId and userName in string type.
	 */

	public Map<Integer, String> getAllUser() {
		Map<Integer, String> userDetails = new HashMap<Integer, String>();
		for (UserBean user : ((UserDao) beanService.getBean("userDao")).getAllUser()) {
			userDetails.put(user.getUsernameID(), user.getUsername());
		}
		return userDetails;
	}

	/**
	 * Service methods to get the user of the application.
	 * 
	 * @param userId,
	 *            userId cannot be negative and should be valid.
	 * @return return the Map object with userId and userName in string type.
	 */

	public UserBean getUser(int userId) {
		if (userId <= 0)
			return null;
		return ((UserDao) beanService.getBean("userDao")).getUserObject(userId);
	}

	/**
	 * Service helper methods to get the list of status.
	 * 
	 * @return return the list object with status in string type.
	 */

	public List<String> getstatusList() {
		List<String> statusList = new ArrayList<String>();
		statusList.add("Submitted");
		statusList.add("Open");
		statusList.add("Progress");
		statusList.add("Close");
		return statusList;
	}

	/**
	 * Service helper methods to get the list of Ranks.
	 * 
	 * @return the Map object with rank and weeks in integer and string type.
	 */

	public Map<Integer, String> getRankList() {
		Map<Integer, String> rankList = new HashMap<Integer, String>();
		rankList.put(1, "1 week");
		rankList.put(2, "2 weeks");
		rankList.put(3, "3 weeks");
		rankList.put(4, "4 weeks");
		rankList.put(5, "More than 4 weeks");
		return rankList;
	}

	/**
	 * Service helper methods to sort list of task.
	 * 
	 * @return the list object with sorted value by status and rank.
	 */

	public List<TaskBean> getSortedTaskList(List<TaskBean> list) {
		Collections.sort(list, new Comparator<TaskBean>() {
			@Override
			public int compare(TaskBean o1, TaskBean o2) {
				Map<String, Integer> statusMap = new HashMap<String, Integer>();
				statusMap.put("Submitted", 1);
				statusMap.put("Open", 2);
				statusMap.put("Progress", 3);
				statusMap.put("Close", 4);
				if (o1.getStatus().equals(o2.getStatus())) {
					return o1.getRank() - o2.getRank();
				}
				return statusMap.get(o1.getStatus()) - statusMap.get(o2.getStatus());
			}
		});
		return list;
	}

	/**
	 * Service helper methods to get the access code of admin and user.
	 * 
	 * @return the Map object in integer and string type.
	 */

	public Map<Integer, String> getAccessList() {
		Map<Integer, String> accessList = new HashMap<Integer, String>();
		accessList.put(1, "ADMIN");
		accessList.put(0, "USER");
		return accessList;
	}

	/**
	 * Service helper methods to set the values in home JSP form.
	 * 
	 * @return the model and view object.
	 */

	public ModelAndView setModelValuesForHomePage(UserBean userbean, ModelAndView model) {
		if (userbean.getAccess() == 1) {
			model.addObject("title", "Welcome Admin");
		} else {
			model.addObject("title", "Welcome To WallMart Task Service");
		}
		model.addObject("userID", userbean.getUsernameID());
		model.addObject("name", userbean.getUsername());
		model.addObject("access", userbean.getAccess());
		model.addObject("userTaskList", getTaskforUser(userbean.getUsernameID()));
		model.setViewName("home");
		return model;
	}

}
