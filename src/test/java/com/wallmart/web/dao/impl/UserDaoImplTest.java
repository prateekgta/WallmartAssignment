package com.wallmart.web.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.wallmart.web.dao.impl.UserDaoImpl;
import com.wallmart.web.model.LoginBean;
import com.wallmart.web.model.UserBean;

public class UserDaoImplTest {
	UserDaoImpl userDaoImpl;

	DataSource mockDataSource = mock(DataSource.class);
	Connection mockConnection = mock(Connection.class);
	PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
	ResultSet mockResultSet = mock(ResultSet.class);

	@Before
	public void setUp() throws Exception {
		userDaoImpl = new UserDaoImpl();
		userDaoImpl.setDataSource(mockDataSource);

		when(mockDataSource.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
		when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
	}

	@Test
	public void testGetAllUserReturnsSameListOfUser_WhenQueryReturnsListOfUser() throws SQLException {
		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		List<UserBean> users = userDaoImpl.getAllUser();
		assertTrue(users.size() > 0);
		assertEquals(2, users.size());
	}

	@Test
	public void testGetAllUserReturnsEmptyListOfUser_WhenQueryReturnsNoUser() throws SQLException {
		when(mockResultSet.next()).thenReturn(false);

		List<UserBean> users = userDaoImpl.getAllUser();
		assertTrue(users.isEmpty());
		assertEquals(0, users.size());
	}

	@Test
	public void testValidateUserReturnsExpectedUser_WhenQueryReturnsValidUser() throws SQLException {
		final int testUsernameId = 1111;
		final int testTaskId = 1010;
		final String testUserEmail = "TestEmail.com";

		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt("usernameID")).thenReturn(testUsernameId);
		when(mockResultSet.getString("email")).thenReturn(testUserEmail);
		when(mockResultSet.getInt("taskID")).thenReturn(testTaskId);

		LoginBean loginBean = mock(LoginBean.class);

		UserBean user = userDaoImpl.validateUser(loginBean);
		assertNotNull(user);
		assertEquals(testUsernameId, user.getUsernameID());
		assertEquals(testUserEmail, user.getEmail());
		assertEquals(testTaskId, user.getAccess());
	}

	@Test
	public void testValidateUserReturnsNull_WhenQueryResultSetIsEmpty() throws SQLException {

		when(mockResultSet.next()).thenReturn(false);

		LoginBean loginBean = mock(LoginBean.class);

		UserBean user = userDaoImpl.validateUser(loginBean);
		assertNull(user);
	}
}
