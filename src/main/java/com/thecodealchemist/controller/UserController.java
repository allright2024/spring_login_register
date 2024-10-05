// src/main/java/com/thecodealchemist/controller/UserController.java
package com.thecodealchemist.controller;

import com.thecodealchemist.entity.User;
import com.thecodealchemist.model.UserLoginRequest;
import com.thecodealchemist.model.UserRegistrationRequest;
import com.thecodealchemist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRequest) {
        boolean isRegistered = userService.registerUser(userRequest);
        if (isRegistered) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.badRequest().body("User registration failed");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest loginRequest) {
        String token = userService.loginUser(loginRequest);
        if (token != null) {
            return ResponseEntity.ok(token);  // JWT 토큰 반환
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}
