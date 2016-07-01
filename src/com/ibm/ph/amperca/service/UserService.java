package com.ibm.ph.amperca.service;

import java.util.List;

import javax.ejb.Local;

import com.ibm.ph.amperca.model.User;

@Local
public interface UserService {

	String getName();

	User add(User user);

	User update(Long id, User user);

	User findById(Long id);

	List<User> getUsers(int startPosition, int maxResults, String sortFields,
			String sortDirections, String search);

	void deleteUser(User user);

	Integer countUser(String searchField);

}
