package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to handle HTTP POST requests to create a new user
    @PostMapping("/signup")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @GetMapping("/get/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        try {
            int userId = userService.login(email, password);
            return ResponseEntity.ok(userId); // Return user ID on successful login
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage()); // Return 401 on failure
        }
    }

    @CrossOrigin(origins = "http://localhost:3000/ForgotPassword")
    @GetMapping("/birthday/{email}")
    public ResponseEntity<?> getBirthdayByEmail(@PathVariable String email) {
        Optional<User> userOptional = userService.findUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO(user.getDateOfBirth());
            return ResponseEntity.ok().body(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
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

