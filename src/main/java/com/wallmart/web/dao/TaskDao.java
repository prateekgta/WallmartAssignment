package com.wallmart.web.dao;

import java.util.List;

import com.wallmart.web.customexception.DataBaseCustomException;
import com.wallmart.web.model.TaskBean;

public interface TaskDao {
	/**
	 * Method used to create a task Object in database.
	 * 
	 * @param taskObject,
	 *            a TaskBean object with taskId set to 0. TaskBean cannot be null or
	 *            empty.
	 * @return a task id.
	 */
	public int addTask(TaskBean taskObject) throws DataBaseCustomException;;

	/**
	 * Method used get task object from database.
	 * 
	 * @param a
	 *            valid userId, userId cannot negative.
	 * @return List of task object
	 */
	List<TaskBean> getTaskforUser(int UserId) throws DataBaseCustomException;

	/**
	 * Method used get task object created by User from database.
	 * 
	 * @param a
	 *            valid userId, userId cannot negative.
	 * @return List of task object
	 */
	List<TaskBean> getTaskCreatedByUser(int UserId) throws DataBaseCustomException;

	/**
	 * Method used to delete task object in database.
	 * 
	 * @param a
	 *            valid taskId, taskId cannot negative.
	 */
	public void deleteTask(int taskid) throws DataBaseCustomException;

	/**
	 * Method used to update task object in database.
	 * 
	 * @param a
	 *            valid task object, Task object cannot be null or empty.
	 */
	public void updateTaskForUser(TaskBean updatedTaskObject);

	/**
	 * Method used get task object of other Users from database.
	 * 
	 * @param a
	 *            valid taskId, cannot negative.
	 * @return task Object
	 */
	public TaskBean getTaskObjectForUser(int taskId);;
}
