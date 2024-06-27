package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.Map;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    // Endpoint to handle HTTP POST requests to create a new user
    @PostMapping("/signup")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PostMapping("/login")
    public int login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        return userService.login(email, password);
    }

    @GetMapping("/get-me")
    public ResponseEntity<?> getCurrentUser(@PathVariable String email) {
        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @CrossOrigin(origins = "http://localhost:3000/ForgotPassword")
    @GetMapping("/birthday/{email}")
    public ResponseEntity<?> getBirthdayByEmail(@PathVariable String email) {
        User user = userService.findUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok().body(new UserDTO(user.getDateOfBirth()));
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String newPassword = requestBody.get("newPassword");
        userService.updatePassword(email, newPassword);
        return ResponseEntity.ok().body("Password updated successfully");
    }

    static class UserDTO {
        private String birthday;

        public UserDTO(String birthday) {
            this.birthday = birthday;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
    }
    @PutMapping("/update-status")
    public void updateStatus(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String status = body.get("status");
        userService.updateStatus(email, status);
    }


}

