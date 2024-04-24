package com.major.cookbook.services;

import java.util.List;

import com.major.cookbook.model.User;

public interface UserService {
		public User addUser(User user);
		//Used to get a list of users returned
		public List<User> getUsers();
		//Used to get the info of a specific user
		public User getUser(int userId);
		//Used to update a user's info
		public User updateUser(User user);		
}
