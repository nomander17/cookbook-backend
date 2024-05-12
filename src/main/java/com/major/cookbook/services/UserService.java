package com.major.cookbook.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.major.cookbook.model.User;

public interface UserService extends UserDetailsService{
	// Add a user
	public User addUser(User user);

	// GET all users
	public List<User> getUsersExceptUserId(int userId);

	// GET specific user by Id
	public User getUserById(int userId);

	// GET specific user by email
	public User getUserByEmail(String email);

	// Update a particular user
	public User updateUser(User user);

	// Delete a particular user
	public User deleteUser(int userId);
	
	//Create Admin if user table is empty
	public User addAdmin();
    
	//Return admin's userId
	public User getAdminUserId();

    public List<User> getAllUsers();

	// Get user by a particular username
    public Object getUserByUsername(String username);
}
