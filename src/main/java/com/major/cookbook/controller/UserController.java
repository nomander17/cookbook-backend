package com.major.cookbook.controller;

import org.springframework.web.bind.annotation.RestController;

import com.major.cookbook.dto.UserDTO;
import com.major.cookbook.model.User;
import com.major.cookbook.services.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;
 
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  // Returns a specified user
  @GetMapping("/{userId}")
  public ResponseEntity<Object> getUser(@PathVariable String userId) {
    User user = this.userService.getUserById(Integer.parseInt(userId));
    if (user == null) {
      return ResponseEntity.notFound().build();
    } else {
    	User admin = this.userService.getAdminUserId();
    	int adminId = admin.getUserId();
    	if(Integer.parseInt(userId)==adminId) {
    		logger.warn("Admin info cannot be accessed");
    		return ResponseEntity.badRequest().body("Admin info cannot be accessed");
    	}else {
    		return ResponseEntity.ok(user);
    	}
    }
  }

  // Returns ALL registered users
  @GetMapping("")
  public ResponseEntity<Object> getUsers() {
	User admin = this.userService.getAdminUserId();
	int adminId = admin.getUserId();
	List<User> users = this.userService.getUsersExceptUserId(adminId);
    if (users.isEmpty()) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(users);
    }

  }

  // Update a specified user
  @PutMapping("/{userId}")
  public ResponseEntity<Object> updateUser(@PathVariable String userId, @RequestBody UserDTO userDTO) {
    User userExist = this.userService.getUserById(Integer.parseInt(userId));
    logger.debug("UserId : {}", userId);
    logger.debug("User: {}", userExist);
    if (userExist == null) {
      logger.warn("UserId {} not found", userId);
      return ResponseEntity.notFound().build();
    }else {
    	User admin = this.userService.getAdminUserId();
    	int adminId = admin.getUserId();
    	if(Integer.parseInt(userId)==adminId) {
    		logger.warn("Admin cannot be updated");
    		return ResponseEntity.badRequest().body("Admin cannot be updated");
    	}else {
	    	User user = new User();
	        user.setUserId(userDTO.getUserId());
	        user.setName(userDTO.getName());
	        user.setUserName(userDTO.getUserName());
	        user.setEmail(userDTO.getEmail());
	        user.setPassword(userDTO.getPassword());
	        user.setAvatar(userDTO.getAvatar());
	        user.setIsAdmin(false);
	        User updatedUser = this.userService.updateUser(user);
	        return ResponseEntity.ok(updatedUser);
    	}
    }
  }

  // Delete a specified user
  @DeleteMapping("/{userId}")
  public ResponseEntity<User> deleteUser(@PathVariable String userId) {
    return ResponseEntity.ok(this.userService.deleteUser(Integer.parseInt(userId)));
  }
}
