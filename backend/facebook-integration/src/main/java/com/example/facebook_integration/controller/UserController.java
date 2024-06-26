package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    // Endpoint to handle HTTP POST requests to create a new resume
    @PostMapping("/create")
    public void createResume(@RequestBody User user) {
        userService.createUser(user);
    }

    @CrossOrigin(origins = "http://localhost:3000/ForgotPassword")
    @GetMapping("/birthday/{email}")
    public ResponseEntity<?> getBirthdayByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
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
    class UserDTO {
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

}

