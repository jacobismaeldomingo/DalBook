package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.User;
import org.springframework.http.HttpStatus;
import com.example.facebook_integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    // Endpoint to handle HTTP POST requests to create a new resume
    @PostMapping("/signup")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

//    @PostMapping("/login")
//    public ResponseEntity<Object>  authenticate(@RequestParam String email, @RequestParam String password) {
//        User authenticateduser = userService.authenticateUser(email, password);
//        if (authenticateduser == null) {
//            return ResponseEntity.ok(authenticateduser);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
//        }
//    }

//    @PostMapping("/login")
//    public ResponseEntity<String>  authenticate(@RequestParam String email, @RequestParam String password) {
//        boolean authenticateduser = userService.authenticateUser2(email, password);
//        if (authenticateduser) {
//            return ResponseEntity.ok("Success");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
//        }
//    }

    @PostMapping("/login")
    public int login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        return userService.login(email, password);
    }


    @PostMapping("/login")
    public int login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        return userService.login(email, password);
    }

}

