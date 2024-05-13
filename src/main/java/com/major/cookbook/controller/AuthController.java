package com.major.cookbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.major.cookbook.dto.UserDTO;
import com.major.cookbook.jwt.JwtRequest;
import com.major.cookbook.jwt.JwtResponse;
import com.major.cookbook.model.User;
import com.major.cookbook.security.JwtHelper;
import com.major.cookbook.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

	@Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> postUserLogin(@RequestBody JwtRequest request) {//Add try catch if user not found
        logger.info(request.toString());
        this.doAuthenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.helper.generateToken(userDetails);        
        JwtResponse response = new JwtResponse(token,userDetails.getUsername());
        return new ResponseEntity<>(response, HttpStatus.OK);
    } 

    private void doAuthenticate(String email, String password) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,password);
		try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
	}

	@PostMapping("/register")
    public ResponseEntity<String> postUserRegister(@RequestBody UserDTO userDTO) {
        if((userService.getUserByEmail(userDTO.getEmail()) != null)) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }
        if((userService.getUserByUsername(userDTO.getUserName()) != null)) {
            return ResponseEntity.badRequest().body("Username already used.");
        }
        else {
            User user = new User();
            user.setName(userDTO.getName());
            user.setUserName(userDTO.getUserName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setIsAdmin(false);
            user.setName(userDTO.getName());
            User newUser = this.userService.addUser(user);
            return ResponseEntity.ok(newUser.toString());
        }        
    }
}
