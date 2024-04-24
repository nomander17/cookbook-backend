package com.major.cookbook.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.major.cookbook.dao.UserDao;
import com.major.cookbook.model.User;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Override
	public User addUser(User user) {
		userDao.save(user);
		return user;
	}
	@Override
	public List<User> getUsers() {
		return userDao.findAll();
	}

	@Override
	public User getUser(int userId) {
		
		return userDao.findById(userId).get();
	}

	@Override
	public User updateUser(User user) {
		userDao.save(user);
		return user;
	}
}
