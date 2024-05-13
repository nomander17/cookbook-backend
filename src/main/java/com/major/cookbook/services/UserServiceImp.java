package com.major.cookbook.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.major.cookbook.repository.UserRepo;
import com.major.cookbook.model.User;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//Retrieving admin details 
	@Value("${admin.username}")
	private String adminUsername;
	@Value("${admin.name}")
	private String adminName;
	@Value("${admin.email}")
	private String adminEmail;
	@Value("${admin.password}")
	private String adminPassword;	
	
	@Override
	public User addUser(User user) {
		userRepo.save(user);
		return user;
	}
	
	//Gets all Users except the Admin
	@Override
	public List<User> getUsersExceptUserId(int userId) {
		return userRepo.findAll()
                .stream()
                .filter(user -> !user.getUserId().equals(userId))
                .collect(Collectors.toList());
	}

	@Override
	public User getUserById(int userId) {
		return userRepo.findById(userId).orElse(null);
	}

	@Override
	public User updateUser(User updatedUser) {
		userRepo.save(updatedUser);
		return updatedUser;
	}

	@Override
	public User deleteUser(int userId) {
		User user = getUserById(userId);
		userRepo.delete(user);
		return user;
	}
	@Override
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email).orElse(null);
	}
	@Override
	public User addAdmin() {
		if(userRepo.count()==0) {
			User admin = new User();
            admin.setUserName(adminUsername);
            admin.setName(adminName);
            admin.setEmail(adminEmail);
            admin.setIsAdmin(true);
            admin.setPassword(passwordEncoder.encode(adminPassword));
			userRepo.save(admin);
			return admin;
		}
		else {
			return null;
		}
	}

	@Override
	public User getAdminUserId() {
		return userRepo.findUserIdByIsAdminTrue();
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	
	@Override
	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username).orElse(null);
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
	}
}
