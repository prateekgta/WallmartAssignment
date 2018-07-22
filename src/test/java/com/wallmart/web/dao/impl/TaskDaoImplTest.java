package com.wallmart.web.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.wallmart.web.model.TaskBean;

public class TaskDaoImplTest {
	TaskDaoImpl taskDaoImpl;

	DataSource mockDataSource = mock(DataSource.class);
	Connection mockConnection = mock(Connection.class);
	PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
	ResultSet mockResultSet = mock(ResultSet.class);

	@Before
	public void setUp() throws Exception {
		taskDaoImpl = new TaskDaoImpl();
		taskDaoImpl.setDataSource(mockDataSource);

		when(mockDataSource.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
		when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
	}

	@Test
	public void testAddTaskWhenTaskObjectIsNull_ThenMethodReturnsNegativeOne() {
		TaskBean task = null;
		int actualTaskId = taskDaoImpl.addTask(task);
		assertEquals(-1, actualTaskId);
	}

	@Test
	public void testAddTaskWhenTaskObjectIsNotNull_ThenMethodReturnsValidAddedTaskId() {
		TaskBean task = new TaskBean();
		int actualTaskId = taskDaoImpl.addTask(task);
		assertTrue(actualTaskId > 0);
	}

	@Test
	public void testGetTaskforUserWhenUserIdIsInvalid_ThenMethodReturnsNull() {
		int userId = -10;
		List<TaskBean> tasksforUser = taskDaoImpl.getTaskforUser(userId);
		assertNull(tasksforUser);
	}

	@Test
	public void testGetTaskforUserWhenUserIdIsValid_ThenMethodReturnsListOfTasksForThatUser() throws SQLException {
		int userId = 1010;
		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		List<TaskBean> tasksForUser = taskDaoImpl.getTaskforUser(userId);
		assertNotNull(tasksForUser);
		assertEquals(2, tasksForUser.size());
	}

	@Test
	public void testDeleteTaskWhenTaskIdIdIsInvalid_ThenMethodReturnsWithoutAnyTaskDeleted() throws SQLException {
		int taskId = -10;
		taskDaoImpl.deleteTask(taskId);
		verify(mockPreparedStatement, never()).executeUpdate();
	}

	@Test
	public void testDeleteTaskWhenTaskIdIdIsValid_ThenMethodDeletesTaskAndExecuteUpdateIsCalled() throws SQLException {
		int taskId = 1010;
		taskDaoImpl.deleteTask(taskId);
		verify(mockPreparedStatement, times(1)).executeUpdate();
	}

	@Test
	public void testGetTaskCreatedByUserWhenUserIdIsInvalid_ThenMethodReturnsNull() {
		int userId = -10;
		List<TaskBean> tasksforUser = taskDaoImpl.getTaskCreatedByUser(userId);
		assertNull(tasksforUser);
	}

	@Test
	public void testGetTaskCreatedByUserWhenUserIdIsValid_ThenMethodReturnsListOfTasksCreatedByThatUser()
			throws SQLException {
		int userId = 1010;
		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		List<TaskBean> tasksForUser = taskDaoImpl.getTaskCreatedByUser(userId);
		assertNotNull(tasksForUser);
		assertEquals(2, tasksForUser.size());
	}

	@Test
	public void testUpdateTaskForUserWhenTaskObjectIsNull_ThenMethodReturnsWithoutUpdatingAnyTask()
			throws SQLException {
		TaskBean task = null;
		taskDaoImpl.updateTaskForUser(task);
		verify(mockPreparedStatement, never()).executeUpdate();
	}

	@Test
	public void testUpdateTaskForUserWhenTaskObjectIsNotNull_ThenMethodUpdatesTaskAndExecuteUpdateIsCalled()
			throws SQLException {
		TaskBean task = new TaskBean();
		taskDaoImpl.updateTaskForUser(task);
		verify(mockPreparedStatement, times(1)).executeUpdate();
	}

	@Test
	public void testGetTaskObjectForUserWhenTaskIdIsInvalid_ThenMethodReturnsNull() {
		int taskId = -10;
		TaskBean taskforUser = taskDaoImpl.getTaskObjectForUser(taskId);
		assertNull(taskforUser);
	}

	@Test
	public void testGetTaskObjectForUserWhenUserIdIsValid_ThenMethodReturnsListOfTasksCreatedByThatUser()
			throws SQLException {
		int taskId = 10;
		int testCreatedUserId = 10;
		int testUserId = 11;
		String testTaskDesc = "testTask";
		String testStatus = "Submitted";
		int testRank = 1;
		int testTaskId = 1;
		String testDate = "testDate";
		String testLastModifiedDate = "testLastModifiedDate";

		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt("createdUserId")).thenReturn(testCreatedUserId);
		when(mockResultSet.getInt("userId")).thenReturn(testUserId);
		when(mockResultSet.getString("task")).thenReturn(testTaskDesc);
		when(mockResultSet.getString("status")).thenReturn(testStatus);
		when(mockResultSet.getInt("rank")).thenReturn(testRank);
		when(mockResultSet.getInt("taskId")).thenReturn(testTaskId);
		when(mockResultSet.getString("createdDate")).thenReturn(testDate);
		when(mockResultSet.getString("lastModifiedDate")).thenReturn(testLastModifiedDate);

		TaskBean actualTaskForUser = taskDaoImpl.getTaskObjectForUser(taskId);
		assertNotNull(actualTaskForUser);
		assertEquals(testCreatedUserId, actualTaskForUser.getCreatedUserId());
		assertEquals(testUserId, actualTaskForUser.getUserId());
		assertEquals(testTaskDesc, actualTaskForUser.getTask());
		assertEquals(testStatus, actualTaskForUser.getStatus());
		assertEquals(testRank, actualTaskForUser.getRank());
		assertEquals(testTaskId, actualTaskForUser.getTaskId());
		assertEquals(testDate, actualTaskForUser.getCreatedDate());
		assertEquals(testLastModifiedDate, actualTaskForUser.getLastModifiedDate());
	}
}
