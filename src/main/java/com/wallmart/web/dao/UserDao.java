package com.wallmart.web.dao;

import java.util.List;
import com.wallmart.web.model.LoginBean;
import com.wallmart.web.model.UserBean;

public interface UserDao {

	/**
	 * Method used to get all the user from database.
	 * 
	 * @return a list of all user
	 */
	List<UserBean> getAllUser();

	/**
	 * Method used to validate user from database.
	 * 
	 * @param login
	 *            object, a login object. login object cannot be null or empty.
	 * @return Object of validated user object.
	 */
	UserBean validateUser(LoginBean login);

	/**
	 * Method used to register a new user in database.
	 * 
	 * @param user
	 *            object need to be registered into database, user object cannot be
	 *            null or empty.
	 */
	void registerUser(UserBean user);

	/**
	 * Method used to get user object from database.
	 * 
	 * @param userId,
	 *            userId cannot be null.
	 * @return Object of user object.
	 */
	UserBean getUserObject(int userId);
}
