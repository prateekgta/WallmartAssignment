package com.wallmart.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.wallmart.web.model.LoginBean;
import com.wallmart.web.model.TaskBean;
import com.wallmart.web.model.UserBean;
import com.wallmart.web.services.UserServices;

/*
 * Controller class
 */

@Controller
public class LoginController {

	@Autowired
	public UserServices userService;
	final static Logger logger = Logger.getLogger(LoginController.class);
	SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/*
	 * redirect to index page.
	 */
	@RequestMapping("/")
	public String index() {
		return "redirect:/index";
	}

	/*
	 * create model object for index.jsp .
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView init() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("msg", "Please Enter Your Login Details");
		return model;
	}

	/*
	 * authorized and save user object into session after index.jsp .
	 */
	@RequestMapping(value = "/indexProcessing", method = RequestMethod.POST)
	public String submit(ModelAndView model, @ModelAttribute("loginBean") LoginBean loginBean,
			HttpServletRequest request) {
		if (loginBean != null && loginBean.getUserName() != null & loginBean.getPassword() != null) {
			UserBean userbean = userService.authenticate(loginBean);
			System.out.println("doing authentication");
			if (userbean != null) {
				request.getSession().setAttribute("userObject", userbean);
				logger.debug("By controller session is saved");
				return "redirect:/home";
			}
			return "redirect:/index";
		}
		return "redirect:/index";
	}

	/*
	 * bind model object with home.jsp.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView homepage(ModelAndView model, HttpServletRequest request) {
		UserBean userbean = (UserBean) request.getSession().getAttribute("userObject");
		return userService.setModelValuesForHomePage(userbean, model);
	}

	/*
	 * bind model object with register.jsp.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("register");
		Map<Integer, String> accessList = userService.getAccessList();
		model.addObject("accessList", accessList);
		model.addObject("user", new UserBean());
		return model;
	}

	/*
	 * save new user object into database after register.jsp submitted request.
	 */
	@RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
	public String addUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("user") UserBean user) {
		userService.registration(user);
		logger.debug("By controller registeration is done");
		return "redirect:/home";
	}

	/*
	 * bind model object with addtask.jsp
	 */
	@RequestMapping(value = "/addtask", method = RequestMethod.GET)
	public ModelAndView addtask(HttpServletRequest request, HttpServletResponse response) {
		UserBean userbean = (UserBean) request.getSession().getAttribute("userObject");
		ModelAndView model = new ModelAndView("addtask");
		Map<Integer, String> userList = null;
		if (userbean.getAccess() == 1) {
			userList = userService.getAllUser();
		} else {
			userList = new HashMap<Integer, String>();
			userList.put(userbean.getUsernameID(), userbean.getUsername());
		}
		model.addObject("userList", userList);
		model.addObject("statusList", userService.getstatusList());
		model.addObject("rankList", userService.getRankList());

		model.addObject("task", new TaskBean());
		return model;
	}

	/*
	 * save new task object into database after addtask.jsp submitted request.
	 */
	@RequestMapping(value = "/addTaskProcess", method = RequestMethod.POST)
	public String addUserTask(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("task") TaskBean task) {
		UserBean userbean = (UserBean) request.getSession().getAttribute("userObject");
		task.setCreatedUserId(userbean.getUsernameID());
		task.setCreatedDate(sdf.format(new Date()));
		task.setLastModifiedDate(sdf.format(new Date()));
		userService.addTask(task);
		logger.debug("By controller task is added" + task);
		return "redirect:/home";
	}

	/*
	 * delete new task object in database after home.jsp submitted request.
	 */
	@RequestMapping(value = "/deleteTask/{id}", method = RequestMethod.GET)
	public String deleteTask(HttpServletRequest request, @PathVariable int id) {
		System.out.println("user id delete");
		userService.deleteTask(id);
		return "redirect:/home";
	}

	/*
	 * update task object in database after home.jsp submitted requested.
	 */
	@RequestMapping(value = "/updateTask/{taskId}", method = RequestMethod.GET)
	public ModelAndView UpdateTask(HttpServletRequest request, @PathVariable int taskId) {
		System.out.println("taskId for update" + taskId);
		ModelAndView model = new ModelAndView("updateTask");
		model.addObject("statusList", userService.getstatusList());
		model.addObject("rankList", userService.getRankList());
		model.addObject("taskObject", userService.getTaskObject(taskId));
		return model;
	}

	/*
	 * update task object in database after taskList.jsp submitted requested.
	 */
	@RequestMapping(value = "/updateTask/updateTaskProcess", method = RequestMethod.POST)
	public String UpdateTaskForUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("taskObject") TaskBean task) {
		task.setLastModifiedDate(sdf.format(new Date()));
		userService.updateTask(task);
		System.out.println("update task for User" + task);
		return "redirect:/home";
	}

	/*
	 * bind value of model for task creation.
	 */
	@RequestMapping(value = "/taskCreatedByAdmin", method = RequestMethod.GET)
	public ModelAndView taskCreatedByAdmin(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("taskList");
		UserBean userbean = (UserBean) request.getSession().getAttribute("userObject");
		logger.debug("task created by admin");
		model.addObject("listofTask", userService.getTaskCreatedByAdmin(userbean.getUsernameID()));
		return model;
	}

	/*
	 * delete session and redirect to index page.
	 */
	@RequestMapping(value = "/logOut", method = RequestMethod.GET)
	public String logOutUser(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("userObject");
		logger.debug("By controller login and session cleaned");
		return "redirect:/index";
	}

}