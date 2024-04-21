package com.major.cookbook.controller;

import org.springframework.web.bind.annotation.RestController;

import com.major.cookbook.model.User;
import com.major.cookbook.services.UserService;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
  @GetMapping("/login")
  public String getUserLogin() {
    return "/user/login";
  }

  @PostMapping("/login")
  public ResponseEntity<String> postUserLogin(@RequestBody String entity) {
    JSONObject request = new JSONObject(entity);
    String email = request.getString("email");
    String password = request.getString("password");
    // vars can now be passed to the service layer
    // login logic here
    // temp returns request
    JSONObject response = new JSONObject();
    response.put("email", email);
    response.put("password", password);
    return ResponseEntity.ok(response.toString());
  }

  @GetMapping("/register")
  public String getUserRegisteString() {
    return "/user/register";
  }
  

  @PostMapping("/register")
  public ResponseEntity<String> postUserRegister(@RequestBody String entity) {
    // todo 
    JSONObject request = new JSONObject(entity);
    JSONObject response = new JSONObject();
    // Register 
    response=request;
    // Generate unique token and store in DB?
    String username=response.getString("username");
    String email=response.getString("email");
    String password=response.getString("password");
    
    User user = new User();

    user.setUserName(username);
    user.setEmail(email);
    user.setPassword(password);

    User registeredUser = userService.addUser(user);
    
    response.put("registeredUser", registeredUser);
    response.put("token", "abcdxyz");
    response.put("success", true);
    response.put("message", "Registration successful.");
    return ResponseEntity.ok(response.toString());
  }
}
  