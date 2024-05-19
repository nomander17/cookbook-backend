package com.major.cookbook.controller;

import org.springframework.web.bind.annotation.RestController;

import com.major.cookbook.dto.UpdateUserProfileDTO;
import com.major.cookbook.dto.UserDTO;
import com.major.cookbook.jwt.JwtResponse;
import com.major.cookbook.model.User;
import com.major.cookbook.security.JwtHelper;
import com.major.cookbook.services.UserService;
import com.major.cookbook.util.UserConversionUtil;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtHelper helper;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  // Returns a specified user
  @GetMapping("/{userId}")
  public ResponseEntity<Object> getUser(@PathVariable String userId, Authentication authentication) {
    User user = this.userService.getUserById(Integer.parseInt(userId));
    if (user == null) {
      return ResponseEntity.notFound().build();
    } else {
      User admin = this.userService.getAdminUserId();
      int adminId = admin.getUserId();
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      if (Integer.parseInt(userId) == adminId) {
        // check if the authenticated user is the admin
        if (userDetails.getUsername().equals(admin.getUsername())) {
          // admin is requesting their own info, allow access
          return ResponseEntity.ok(UserConversionUtil.convertToPublicUserDTO(user));
        } else {
          // non-admin user is trying to access admin info, deny access
          logger.warn("Admin info cannot be accessed");
          return ResponseEntity.badRequest().body("Admin info cannot be accessed");
        }
      } else {
        // Non-admin user info can be accessed by anyone
        return ResponseEntity.ok(UserConversionUtil.convertToPublicUserDTO(user));
      }
    }
  }

  //View User Profile
  @GetMapping("/{userId}/profile")
  public ResponseEntity<Object> getUserProfile(@PathVariable String userId, Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String authUsername = userDetails.getUsername();
    User user = userService.getUserById(Integer.parseInt(userId));
    String username = user.getUsername();
    if (authUsername.equals(username)) {
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient permissions to fetch user profile.");
  }

  //Edit User Profile
  @PutMapping("/{userId}/profile")
  public ResponseEntity<Object> editUserProfile(@PathVariable String userId,@RequestBody UpdateUserProfileDTO updateUserProfileDTO, Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String authUsername = userDetails.getUsername();
    User oldUser = userService.getUserById(Integer.parseInt(userId));
    String username = oldUser.getUsername();
    if (authUsername.equals(username)) {
        oldUser.setName(updateUserProfileDTO.getName());
        oldUser.setUserName(updateUserProfileDTO.getUsername());
        oldUser.setEmail(updateUserProfileDTO.getEmail());
        oldUser.setAvatar(updateUserProfileDTO.getAvatar());
        User updatedUser = userService.updateUser(oldUser);
        // instead of returning a new user body, it should return a new auth token
        UserDetails newUserDetails = this.userDetailsService.loadUserByUsername(updatedUser.getUsername());
        String token = this.helper.generateToken(newUserDetails);
        JwtResponse response = new JwtResponse(token, userDetails.getUsername(), Integer.parseInt(userId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient permissions to fetch user profile.");
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
    } else {
      User admin = this.userService.getAdminUserId();
      int adminId = admin.getUserId();
      if (Integer.parseInt(userId) == adminId) {
        logger.warn("Admin cannot be updated");
        return ResponseEntity.badRequest().body("Admin cannot be updated");
      } else {
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
  public ResponseEntity<Object> deleteUser(@PathVariable String userId, Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String authUsername = userDetails.getUsername();
    User user = userService.getUserById(Integer.parseInt(userId));
    String username = user.getUsername();
    if (authUsername.equals(username)) {
      return ResponseEntity.ok(this.userService.deleteUser(Integer.parseInt(userId)));
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient permissions to fetch user profile.");
  }
}
