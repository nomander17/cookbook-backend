package com.major.cookbook.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.major.cookbook.repository.UserRepo;
import com.major.cookbook.model.User;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public User addUser(User user) {
		userRepo.save(user);
		return user;
	}
	@Override
	public List<User> getUsers() {
		return userRepo.findAll();
	}

	@Override
	public User getUser(int userId) {		
		return userRepo.findById(userId).get();
	}

	@Override
	public User updateUser(User user) {
		userRepo.save(user);
		return user;
	}
}
