package com.major.cookbook.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.major.cookbook.repository.UserRepo;
import com.major.cookbook.model.User;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserRepo userRepo;
	
	//Retrieving admin details 
	@Value("${admin.username}")
	private String adminUsername;
	@Value("${admin.email}")
	private String adminEmail;
	@Value("${admin.password}")
	private String adminPassword;	
	
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
	public User getUserById(int userId) {
		return userRepo.findById(userId).orElse(null);
	}

	@Override
	public User updateUser(User updatedUser) {
		userRepo.save(updatedUser);
		return updatedUser;
	}

	@Override
	public User deleteUser(int userID) {
		User user = getUserById(userID);
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
            admin.setUsername(adminUsername);
            admin.setEmail(adminEmail);
            admin.setIs_admin(true);
            admin.setPassword(adminPassword);
			userRepo.save(admin);
			return admin;
		}
		else {
			return null;
		}
	}
}
