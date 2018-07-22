package com.wallmart.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.wallmart.web.customexception.ServiceCustomException;
import com.wallmart.web.dao.impl.TaskDaoImpl;
import com.wallmart.web.dao.impl.UserDaoImpl;
import com.wallmart.web.model.LoginBean;
import com.wallmart.web.model.TaskBean;
import com.wallmart.web.model.UserBean;
import com.wallmart.web.services.GetBeanService;
import com.wallmart.web.services.UserServices;

@SuppressWarnings("unused")
public class UserServicesTest {

	@Mock
	private GetBeanService getBeanService;

	@InjectMocks
	@Resource
	private UserServices userServices;;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSetModelValuesForHomePageSetsTitleForAdmin_WhenUserAccessIs1() {
		UserBean user = new UserBean();
		user.setUsername("test");
		user.setPassword("test");
		user.setEmail("test@email.com");
		user.setAccess(1);

		ModelAndView mv = new ModelAndView();

		String expectedTitle = "Welcome Admin";
		String actualTitle = (String) userServices.setModelValuesForHomePage(user, mv).getModel().get("title");
		assertEquals(expectedTitle, actualTitle);
	}

	@Test
	public void testSetModelValuesForHomePageSetsTitleForGeneralUser_WhenUserAccessIsNot1() {
		UserBean user = new UserBean();
		user.setUsername("test");
		user.setPassword("test");
		user.setEmail("test@email.com");
		user.setAccess(2);

		ModelAndView mv = new ModelAndView();

		String expectedTitle = "Welcome To WallMart Task Service";
		String actualTitle = (String) userServices.setModelValuesForHomePage(user, mv).getModel().get("title");
		assertEquals(expectedTitle, actualTitle);
	}

	@Test(expected = ServiceCustomException.class)
	public void testAuthenticateWhenLoginBeanNull_ThenMethodThrowsException() {
		LoginBean loginBean = null;
		UserBean user = userServices.authenticate(loginBean);
	}

	@Test(expected = ServiceCustomException.class)
	public void testAuthenticateWhenUserNameNull_ThenMethodThrowsException() {
		LoginBean loginBean = new LoginBean();
		loginBean.setUserName(null);
		loginBean.setPassword("test");
		UserBean user = userServices.authenticate(loginBean);
	}

	@Test(expected = ServiceCustomException.class)
	public void testAuthenticateWhenUserIsEmpty_ThenMethodThrowsException() {
		LoginBean loginBean = new LoginBean();
		loginBean.setUserName("");
		loginBean.setPassword("test");
		UserBean user = userServices.authenticate(loginBean);
	}

	@Test(expected = ServiceCustomException.class)
	public void testAuthenticateWhenUserIsBlank_ThenMethodThrowsException() {
		LoginBean loginBean = new LoginBean();
		loginBean.setUserName("   ");
		loginBean.setPassword("test");
		UserBean user = userServices.authenticate(loginBean);
	}

	@Test(expected = ServiceCustomException.class)
	public void testAuthenticateWhenPasswordsIsNull_ThenMethodThrowsException() {
		LoginBean loginBean = new LoginBean();
		loginBean.setUserName("test");
		loginBean.setPassword(null);
		UserBean user = userServices.authenticate(loginBean);
	}

	@Test(expected = ServiceCustomException.class)
	public void testAuthenticateWhenPasswordsIsEmpty_ThenMethodThrowsException() {
		LoginBean loginBean = new LoginBean();
		loginBean.setUserName("test");
		loginBean.setPassword("");
		UserBean user = userServices.authenticate(loginBean);
	}

	@Test(expected = ServiceCustomException.class)
	public void testAuthenticateWhenPasswordsIsBlank_ThenMethodThrowsException() {
		LoginBean loginBean = new LoginBean();
		loginBean.setUserName("test");
		loginBean.setPassword("  ");
		UserBean user = userServices.authenticate(loginBean);
	}

	@Test
	public void testAuthenticateWhenAllConditionsValid_ThenMethodReturnsValidUser() {
		final String testUserName = "test";
		final String testPassword = "testPassword";
		LoginBean loginBean = new LoginBean();
		loginBean.setUserName(testUserName);
		loginBean.setPassword(testPassword);

		UserBean userBean = new UserBean();
		userBean.setUsername(testUserName);
		userBean.setPassword(testPassword);

		UserDaoImpl mockUserDao = mock(UserDaoImpl.class);
		when(getBeanService.getBean("userDao")).thenReturn(mockUserDao);
		when(mockUserDao.validateUser(loginBean)).thenReturn(userBean);

		UserBean user = userServices.authenticate(loginBean);
		assertNotNull(user);
		assertEquals(testUserName, user.getUsername());
		assertEquals(testPassword, user.getPassword());
	}

	@Test
	public void testRegistrationWhenUserIsNull_ThenRegistrationMethodInUserDaoIsNotCalled() {
		UserDaoImpl mockUserDao = mock(UserDaoImpl.class);
		when(getBeanService.getBean("userDao")).thenReturn(mockUserDao);

		UserBean user = null;
		userServices.registration(user);
		verify(mockUserDao, never()).registerUser(user);
	}

	@Test
	public void testRegistrationWhenUserIsNotNull_ThenRegistrationMethodInUserDaoIsCalled() {
		UserDaoImpl mockUserDao = mock(UserDaoImpl.class);
		when(getBeanService.getBean("userDao")).thenReturn(mockUserDao);

		UserBean user = new UserBean();
		userServices.registration(user);
		verify(mockUserDao, times(1)).registerUser(user);
	}

	@Test
	public void testAddTaskWhenTaskIsNull_ThenMethodReturnReturnNegativeOne() {
		TaskBean task = null;
		int actualVal = userServices.addTask(task);
		assertEquals(-1, actualVal);
	}

	@Test
	public void testAddTaskWhenTaskIsNotNull_ThenMethodReturnReturnObject() {
		TaskBean task = new TaskBean();
		TaskDaoImpl mockTaskDao = mock(TaskDaoImpl.class);
		when(getBeanService.getBean("taskDao")).thenReturn(mockTaskDao);
		when(mockTaskDao.addTask(task)).thenReturn(1);
		int actualVal = userServices.addTask(task);
		assertTrue(actualVal > 0);
	}

	@Test
	public void testGetTaskforUserWhenUserIdIsNegative_ThenMethodReturnNull() {
		final int userId = -10;
		List<TaskBean> taskList = userServices.getTaskforUser(userId);
		assertNull(taskList);
	}

	@Test
	public void testGetTaskforUserWhenUserIdIsPositive_ThenMethodReturnObject() {
		final int userId = 1010;
		List<TaskBean> taskList = new ArrayList<TaskBean>();
		taskList.add(new TaskBean());

		TaskDaoImpl mockTaskDao = mock(TaskDaoImpl.class);
		when(getBeanService.getBean("taskDao")).thenReturn(mockTaskDao);
		when(mockTaskDao.getTaskforUser(userId)).thenReturn(taskList);

		List<TaskBean> actualTaskList = userServices.getTaskforUser(userId);
		assertNotNull(actualTaskList);
		assertTrue(actualTaskList.size() > 0);
		assertEquals(taskList, actualTaskList);
	}

	@Test
	public void testGetTaskCreatedByAdminWhenUserIdIsNegative_ThenMethodReturnNull() {
		final int adminUserId = -10;
		List<TaskBean> taskList = userServices.getTaskforUser(adminUserId);
		assertNull(taskList);
	}

	@Test
	public void testGetTaskCreatedByAdminWhenUserIdIsPositive_ThenMethodReturnObject() {
		final int adminUserId = 1010;
		List<TaskBean> taskList = new ArrayList<TaskBean>();
		taskList.add(new TaskBean());

		TaskDaoImpl mockTaskDao = mock(TaskDaoImpl.class);
		when(getBeanService.getBean("taskDao")).thenReturn(mockTaskDao);
		when(mockTaskDao.getTaskCreatedByUser(adminUserId)).thenReturn(taskList);

		List<TaskBean> actualTaskList = userServices.getTaskCreatedByAdmin(adminUserId);
		assertNotNull(actualTaskList);
		assertTrue(actualTaskList.size() > 0);
		assertEquals(taskList, actualTaskList);
	}

	@Test
	public void testUpdateTaskWhenTaskObjectIsNull_ThenUpdateTaskMethodInTaskDaoIsNotCalled() {
		TaskDaoImpl mockTaskDao = mock(TaskDaoImpl.class);
		when(getBeanService.getBean("taskDao")).thenReturn(mockTaskDao);

		TaskBean updateTask = null;
		userServices.updateTask(updateTask);
		verify(mockTaskDao, never()).updateTaskForUser(updateTask);
	}

	@Test
	public void testUpdateTaskWhenTaskObjectIsNotNull_ThenUpdateTaskMethodInTaskDaoIsCalled() {
		TaskDaoImpl mockTaskDao = mock(TaskDaoImpl.class);
		when(getBeanService.getBean("taskDao")).thenReturn(mockTaskDao);

		TaskBean updateTask = new TaskBean();
		userServices.updateTask(updateTask);
		verify(mockTaskDao, times(1)).updateTaskForUser(updateTask);
	}

	@Test
	public void testGetTaskObjectWhenTaskIdIsNegative_ThenMethodReturnNull() {
		final int taskId = -10;
		TaskBean actualTask = userServices.getTaskObject(taskId);
		assertNull(actualTask);
	}

	@Test
	public void testGetTaskObjectWhenTaskIdIsPositive_ThenMethodReturnsTaskObject() {
		final int taskId = 1010;
		TaskBean task = new TaskBean();

		TaskDaoImpl mockTaskDao = mock(TaskDaoImpl.class);
		when(getBeanService.getBean("taskDao")).thenReturn(mockTaskDao);
		when(mockTaskDao.getTaskObjectForUser(taskId)).thenReturn(task);

		TaskBean actualTask = userServices.getTaskObject(taskId);
		assertNotNull(actualTask);
		assertEquals(task, actualTask);
	}

	@Test
	public void testGetUserWhenUserIdIsNegative_ThenMethodReturnNull() {
		final int userId = -10;
		UserBean actualUser = userServices.getUser(userId);
		assertNull(actualUser);
	}

	@Test
	public void testGetUserWhenTaskIdIsPositive_ThenMethodReturnsUserBeanObject() {
		final int userId = 1010;
		UserBean user = new UserBean();

		UserDaoImpl mockUserDao = mock(UserDaoImpl.class);
		when(getBeanService.getBean("userDao")).thenReturn(mockUserDao);
		when(mockUserDao.getUserObject(userId)).thenReturn(user);

		UserBean actualUser = userServices.getUser(userId);
		assertNotNull(actualUser);
		assertEquals(user, actualUser);
	}
}
