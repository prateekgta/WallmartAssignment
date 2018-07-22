package com.wallmart.web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import com.wallmart.web.customexception.DataBaseCustomException;
import com.wallmart.web.dao.UserDao;
import com.wallmart.web.model.LoginBean;
import com.wallmart.web.model.UserBean;

public class UserDaoImpl implements UserDao {

	private DataSource dataSource;

	/**
	 * Method used by framework to set data source object .
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserBean> getAllUser() {
		// TODO Auto-generated method stub
		List<UserBean> listOfUser = new ArrayList<UserBean>();

		String sql = "SELECT * FROM WallmartDatabase.user";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			UserBean user = null;
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				user = new UserBean(rs.getInt("usernameID"), rs.getString("username"), rs.getString("email"),
						rs.getInt("taskID"));
				listOfUser.add(user);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new DataBaseCustomException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return listOfUser;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserBean validateUser(LoginBean login) {
		String sql = "SELECT * FROM WallmartDatabase.user WHERE username = ? and password = ?";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, login.getUserName());
			ps.setString(2, login.getPassword());
			UserBean user = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new UserBean(rs.getInt("usernameID"), login.getUserName(), rs.getString("email"),
						login.getPassword(), rs.getInt("taskID"));
			}
			rs.close();
			ps.close();
			return user;
		} catch (SQLException e) {
			throw new DataBaseCustomException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerUser(UserBean user) {
		String sql = "insert into WallmartDatabase.user"
				+ "(usernameID, username, email, password, taskID) values(?,?,?,?,?)";
		Connection conn = null;
		Random rand = new Random();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, rand.nextInt(1000));
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setInt(5, user.getAccess());
			ps.executeUpdate();
			System.out.println("Record is inserted into DBUSER table!");
			ps.close();

		} catch (SQLException e) {
			throw new DataBaseCustomException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		// TODO Auto-generated method stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserBean getUserObject(int userId) {
		// TODO Auto-generated method stub
		List<UserBean> listOfUser = new ArrayList<UserBean>();

		String sql = "SELECT * FROM WallmartDatabase.user where usernameID = ?";
		Connection conn = null;
		UserBean user = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				user = new UserBean(rs.getInt("usernameID"), rs.getString("username"), rs.getString("email"),
						rs.getInt("taskID"));
				listOfUser.add(user);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new DataBaseCustomException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return user;
	}

}
