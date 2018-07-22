package com.wallmart.web.model;

/**
 * @author Prateek Gupta
 * Bean class of Task mapped with Database.
 */

public class TaskBean{
	int createdUserId;
	int userId;
	String task;
	String status;
	int rank;
	int taskId;
	String createdDate;
	String lastModifiedDate;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public TaskBean() {
		super();
	}

	
	
	public TaskBean(int createdUserId, int userId, String task, String status, int rank, int taskId, String createdDate,
			String lastModifiedDate) {
		super();
		this.createdUserId = createdUserId;
		this.userId = userId;
		this.task = task;
		this.status = status;
		this.rank = rank;
		this.taskId = taskId;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(int createdUserId) {
		this.createdUserId = createdUserId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public String toString() {
		return "TaskBean [createdUserId=" + createdUserId + ", userId=" + userId + ", task=" + task + ", status="
				+ status + ", rank=" + rank + ", taskId=" + taskId + ", createdDate=" + createdDate
				+ ", lastModifiedDate=" + lastModifiedDate + "]";
	}

	

}
