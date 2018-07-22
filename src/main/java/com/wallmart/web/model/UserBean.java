package com.wallmart.web.model;

/**
 * @author Prateek Gupta
 * Bean class of User mapped with Database.
 */

public class UserBean {
	
	private int usernameID;
	private String username;
	private String email;
	private String password;
	private int access;
	
	public UserBean() {
		// TODO Auto-generated constructor stub
	}
	
	public UserBean(int usernameID, String username, String email, String password, int access) {
		this.usernameID = usernameID;
		this.username = username;
		this.email = email;
		this.password = password;
		this.access = access;
	}
	
	public UserBean(int usernameID, String username, String email, int access) {
		this.usernameID = usernameID;
		this.username = username;
		this.email = email;
		this.access = access;
	}
	
	public int getUsernameID() {
		return usernameID;
	}
	public void setUsernameID(int usernameID) {
		this.usernameID = usernameID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAccess() {
		return access;
	}
	public void setAccess(int access) {
		this.access = access;
	}
	
	@Override
	public String toString() {
		return "UserBean [usernameID=" + usernameID + ", username=" + username + ", email=" + email + ", password="
				+ password + ", access=" + access + "]";
	}
	
}
