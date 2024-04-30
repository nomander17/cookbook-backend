package com.major.cookbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.major.cookbook.dto.UserDTO;
import com.major.cookbook.model.User;
import com.major.cookbook.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> postUserLogin(@RequestBody User user) {
        return ResponseEntity.ok(user.toString());
    } 

    @PostMapping("/register")
    public ResponseEntity<String> postUserRegister(@RequestBody UserDTO userDTO) {
        User exisitingUser = userService.getUserByEmail(userDTO.getEmail());
        if (exisitingUser != null) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }else {
            User user = new User();
            user.setName(userDTO.getName());
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setIsAdmin(false);
            user.setName(userDTO.getName());
            User newUser = this.userService.addUser(user);
            return ResponseEntity.ok(newUser.toString());
        }
        
    }
}
