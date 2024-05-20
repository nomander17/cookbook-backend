package com.major.cookbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.major.cookbook.dto.EmailDTO;
import com.major.cookbook.dto.OtpDTO;
import com.major.cookbook.dto.PublicUserDTO;
import com.major.cookbook.dto.ResetPasswordDTO;
import com.major.cookbook.dto.UserDTO;
import com.major.cookbook.jwt.JwtRequest;
import com.major.cookbook.jwt.JwtResponse;
import com.major.cookbook.model.User;
import com.major.cookbook.security.JwtHelper;
import com.major.cookbook.services.ResetService;
import com.major.cookbook.services.UserService;
import com.major.cookbook.util.UserConversionUtil;

import io.jsonwebtoken.Jwts;

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

    @Autowired
    private ResetService resetService;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> postUserLogin(@RequestBody JwtRequest request) {
        logger.info(request.toString());
        try {
            this.doAuthenticate(request.getUsername(), request.getPassword());
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        this.doAuthenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String username = request.getUsername();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        String token = this.helper.generateToken(userDetails);
        JwtResponse response = new JwtResponse(token, userDetails.getUsername(), userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> postUserRegister(@RequestBody UserDTO userDTO) {
        if ((userService.getUserByEmail(userDTO.getEmail()) != null)) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }
        if ((userService.getUserByUsername(userDTO.getUserName()) != null)) {
            return ResponseEntity.badRequest().body("Username already used.");
        } else {
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

    @PostMapping("/generate-otp")
    public ResponseEntity<Object> sendOtp(@RequestBody EmailDTO emailDTO) {
        String email = emailDTO.getEmail();
        logger.debug("/generate-otp email:", email);
        if (userService.getUserByEmail(email) == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email isn't registered.");
        else {
            try {
                String token = resetService.generateJwt(false, email); // this false represents that otp not verified
                User user = userService.getUserByEmail(email);
                int userId = user.getUserId();
                String username = user.getUsername();
                JwtResponse response = new JwtResponse(token, username, userId);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error generating OTP. Please try again.");
            }
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Object> verifyOtp(@RequestHeader("Authorization") String token, @RequestBody OtpDTO otpDTO) {
        String otp = otpDTO.toString();
        boolean isOtpValid = resetService.verifyOtp(token.substring(7), otp);
        if (isOtpValid) {
            System.out.println("Valid OTP");
            String username = Jwts.parserBuilder().setSigningKey(helper.getSecretKey()).build()
                    .parseClaimsJws(token.substring(7)).getBody().getSubject();
            String newToken = resetService.generateJwt(true, username);
            User user = userService.getUserByUsername(username);
            int userId = user.getUserId();
            JwtResponse response = new JwtResponse(newToken, username, userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestHeader("Authorization") String token,
            @RequestBody ResetPasswordDTO resetPasswordDTO) {
        String newPassword = resetPasswordDTO.toString();
        try {
            String verified = Jwts.parserBuilder().setSigningKey(helper.getSecretKey()).build()
                    .parseClaimsJws(token.substring(7)).getBody().get("verified", String.class);
            if (verified.equals("yes")) {
                String username = Jwts.parserBuilder().setSigningKey(helper.getSecretKey()).build()
                        .parseClaimsJws(token.substring(7)).getBody().getSubject();
                User user = userService.getUserByUsername(username);
                user.setPassword(passwordEncoder.encode(newPassword));
                User updatedUser = this.userService.updateUser(user);
                PublicUserDTO publicUpdatedUser = UserConversionUtil.convertToPublicUserDTO(updatedUser);
                return ResponseEntity.ok(publicUpdatedUser);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized for re-setting password" + e);
        }
        return null;

    }

    // checking if the user as the admin role
    @GetMapping("/is-admin")
    public ResponseEntity<Boolean> isAdmin(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(user.getIsAdmin());
    }
}
