package com.ibm.ph.amperca.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.ibm.ph.amperca.model.User;
import com.ibm.ph.amperca.repository.UserRepository;

@Stateless
public class UserServiceImpl implements UserService {

	@Inject
	UserRepository userRepository;

	public UserServiceImpl() {

	}

	public String getName() {
		return userRepository.getName();
	}

	@Override
	public User add(User user) {
		return userRepository.add(user);
	}

	@Override
	public User update(Long id, User user) {
		User u = userRepository.update(id, user);
		return u;
	}

	@Override
	public User findById(Long id) {
		User user = userRepository.findById(id);
		return user;
	}

	@Override
	public void deleteUser(User user) {
		userRepository.deleteUser(user);
	}

	@Override
	public Integer countUser(String searchField) {
		// TODO Auto-generated method stub
		return userRepository.countUser(searchField).intValue();
	}

	@Override
	public List<User> getUsers(int startPosition, int maxResults,
			String sortFields, String sortDirections, String searchField) {
		return userRepository.getUsers(startPosition, maxResults, sortFields,
				sortDirections, searchField);
	}

}
