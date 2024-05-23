package com.major.cookbook.controller;

import org.springframework.web.bind.annotation.RestController;

import com.major.cookbook.dto.UpdateUserProfileDTO;
import com.major.cookbook.jwt.JwtResponse;
import com.major.cookbook.model.Post;
import com.major.cookbook.model.User;
import com.major.cookbook.security.JwtHelper;
import com.major.cookbook.services.LikeService;
import com.major.cookbook.services.PostService;
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
  private PostService postService;

  @Autowired
  private LikeService likeService;

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
      //User admin = this.userService.getAdminUserId();
      //int adminId = admin.getUserId();
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      String username = userDetails.getUsername();
      User authUser = this.userService.getUserByUsername(username);
      if (user.getIsAdmin()) {
        // check if user trying to access admin info
        if (user.getUsername().equals(authUser.getUsername())) {
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

  //GETs all posts by the user
  @GetMapping("/{userId}/posts")
  public ResponseEntity<Object> getUserPosts(@PathVariable String userId){
    User user = userService.getUserById(Integer.parseInt(userId));
    List<Post> posts = postService.findPostByUser(user);
    for (Post post: posts) {
      post.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(post.getUser()));
    }
    return ResponseEntity.ok(posts);
  }

  //GETs all posts liked by the user
  @GetMapping("{userId}/liked-posts")
  public ResponseEntity<Object> getLikedPosts(@PathVariable String userId){
    List<Post> posts = likeService.getPostsLikedByUser(Integer.parseInt(userId));
    for (Post post: posts) {
      post.setPublicUserDTO(UserConversionUtil.convertToPublicUserDTO(post.getUser()));
    }
    return ResponseEntity.ok(posts);
  }

  //Edit User Profile
  @PutMapping("/{userId}/profile")
  public ResponseEntity<Object> editUserProfile(@PathVariable String userId,@RequestBody UpdateUserProfileDTO updateUserProfileDTO, Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String authUsername = userDetails.getUsername();
    User oldUser = userService.getUserById(Integer.parseInt(userId));
    String username = oldUser.getUsername();
    String oldemail = oldUser.getEmail();
    if(((!username.equals(updateUserProfileDTO.getUsername()))&&(userService.getUserByUsername(updateUserProfileDTO.getUsername()))!=null) || ((!oldemail.equals(updateUserProfileDTO.getEmail())) && (userService.getUserByEmail(updateUserProfileDTO.getEmail()))!=null)){
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Username or Email already exists");
    }
    if (authUsername.equals(username)) {
        oldUser.setName(updateUserProfileDTO.getName());
        oldUser.setUserName(updateUserProfileDTO.getUsername());
        oldUser.setEmail(updateUserProfileDTO.getEmail());
        oldUser.setAvatar(updateUserProfileDTO.getAvatar());
        User updatedUser = userService.updateUser(oldUser);
        // instead of returning a new user body, it should return a new auth token
        UserDetails newUserDetails = this.userDetailsService.loadUserByUsername(updatedUser.getUsername());
        String token = this.helper.generateToken(newUserDetails);
        JwtResponse response = new JwtResponse(token, newUserDetails.getUsername(), Integer.parseInt(userId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient permissions to fetch user profile.");
  }

  // Delete a specified user
  @DeleteMapping("/{userId}")
  public ResponseEntity<Object> deleteUser(@PathVariable String userId, Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String authUsername = userDetails.getUsername();
    User user = userService.getUserById(Integer.parseInt(userId));
    String username = user.getUsername();
    if (authUsername.equals(username)) {
      if (user.getIsAdmin()){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admin profile cannot be deleted");
      }
      return ResponseEntity.ok(this.userService.deleteUser(Integer.parseInt(userId)));
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient permissions to fetch user profile.");
  }
}
