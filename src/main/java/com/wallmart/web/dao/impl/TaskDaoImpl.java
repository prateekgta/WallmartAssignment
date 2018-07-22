package com.wallmart.web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.sql.*;
import com.wallmart.web.customexception.DataBaseCustomException;
import com.wallmart.web.dao.TaskDao;
import com.wallmart.web.model.TaskBean;

public class TaskDaoImpl implements TaskDao {

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
	public int addTask(TaskBean taskObject) {
		if(taskObject == null) {
			return -1;
		}
		String sql = "insert into WallmartDatabase.task"
				+ "(createdUserId, userId, task, status, rank, taskId, createdDate, lastModifiedDate) values(?,?,?,?,?,?,?,?)";
		Random rand = new Random();
		Connection conn = null;
		int taskid = rand.nextInt(10000);
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, taskObject.getCreatedUserId());
			ps.setInt(2, taskObject.getUserId());
			ps.setString(3, taskObject.getTask());
			ps.setString(4, taskObject.getStatus());
			ps.setInt(5, taskObject.getRank());
			ps.setInt(6, taskid);
			ps.setString(7, taskObject.getCreatedDate());
			ps.setString(8, taskObject.getLastModifiedDate());
			ps.executeUpdate();
			System.out.println("Record is inserted into Task table!");
			ps.close();
			return taskid;
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
	public List<TaskBean> getTaskforUser(int userId) {
		if(userId <= 0) return null;
		String sql = "SELECT * FROM WallmartDatabase.task WHERE userId = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			List<TaskBean> listOfTaskOfUser = new ArrayList<TaskBean>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TaskBean task = new TaskBean(rs.getInt("createdUserId"), rs.getInt("userId"), rs.getString("task"),
						rs.getString("status"), rs.getInt("rank"), rs.getInt("taskId"), rs.getString("createdDate"),
						rs.getString("lastModifiedDate"));
				listOfTaskOfUser.add(task);
			}
			rs.close();
			ps.close();
			return listOfTaskOfUser;
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
	public void deleteTask(int taskid) {
		if(taskid <= 0) return;
		String sql="delete from WallmartDatabase.task where taskId= ?";  
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, taskid);
			ps.executeUpdate();
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
	}

	/**
	 * {@inheritDoc}
	*/
	@Override
	public List<TaskBean> getTaskCreatedByUser(int UserId) {
		if(UserId <= 0) return null;
		String sql = "SELECT * FROM WallmartDatabase.task WHERE createdUserId = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, UserId);
			List<TaskBean> listOfTaskOfUser = new ArrayList<TaskBean>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TaskBean task = new TaskBean(rs.getInt("createdUserId"), rs.getInt("userId"), rs.getString("task"),
						rs.getString("status"), rs.getInt("rank"), rs.getInt("taskId"),rs.getString("createdDate"),
						rs.getString("lastModifiedDate"));
				listOfTaskOfUser.add(task);
				System.out.println("task"+task);
			}
			rs.close();
			ps.close();
			return listOfTaskOfUser;
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
	public void updateTaskForUser(TaskBean taskObject) {
		if(taskObject == null) return;
		String sql = "Update WallmartDatabase.task SET task = ?,status= ?,rank= ?,lastModifiedDate= ? where taskId = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, taskObject.getTask());
			ps.setString(2, taskObject.getStatus());
			ps.setInt(3, taskObject.getRank());
			ps.setString(4, taskObject.getLastModifiedDate());
			ps.setInt(5, taskObject.getTaskId());
			ps.executeUpdate();
			System.out.println("Record is updated into Task table!");
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
	}

	/**
	 * {@inheritDoc}
	*/
	@Override
	public TaskBean getTaskObjectForUser(int taskId) {
		if(taskId <= 0) return null;
		String sql = "SELECT * FROM WallmartDatabase.task WHERE taskId = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, taskId);
			TaskBean task = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				 task = new TaskBean(rs.getInt("createdUserId"), rs.getInt("userId"), rs.getString("task"),
						rs.getString("status"), rs.getInt("rank"), rs.getInt("taskId"),rs.getString("createdDate"),
						rs.getString("lastModifiedDate"));
			}
			rs.close();
			ps.close();
			return task;
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

}
