package com.major.cookbook.services;

import java.util.List;

import com.major.cookbook.model.User;

public interface UserService {
	// Add a user
	public User addUser(User user);

	// GET all users
	public List<User> getUsersExceptUserId(int userId);

	// GET specific user by ID
	public User getUserById(int userId);

	// GET specific user by email
	public User getUserByEmail(String email);

	// Update a particular user
	public User updateUser(User user);

	// Delete a particular user
	public User deleteUser(int userID);
	
	//Create Admin if user table is empty
	public User addAdmin();
    
	//Return admin's userID
	public User getAdminUserId();
}
