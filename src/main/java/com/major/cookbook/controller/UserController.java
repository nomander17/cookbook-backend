package com.major.cookbook.controller;

import org.springframework.web.bind.annotation.RestController;
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
  @GetMapping("/{userID}")
  public ResponseEntity<Object> getUser(@PathVariable String userID) {
    User user = this.userService.getUserById(Integer.parseInt(userID));
    if (user == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(user);
    }
  }

  // Returns ALL registered users
  @GetMapping("/")
  public ResponseEntity<Object> getUsers() {
    List<User> users = this.userService.getUsers();
    if (users.isEmpty()) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(users);
    }

  }

  // Update a specified user
  @PutMapping("/{userID}")
  public ResponseEntity<Object> updateUser(@PathVariable String userID, @RequestBody User updatedUser) {
    User user = this.userService.getUserById(Integer.parseInt(userID));
    logger.debug("UserID : {}", userID);
    logger.debug("User: {}", user);
    if (user == null) {
      logger.warn("UserID {} not found", userID);
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(this.userService.updateUser(updatedUser));
    }
  }

  // Delete a specified user
  @DeleteMapping("/{userID}")
  public ResponseEntity<User> deleteUser(@PathVariable String userID) {
    return ResponseEntity.ok(this.userService.deleteUser(Integer.parseInt(userID)));
  }
}
