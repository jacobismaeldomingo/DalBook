package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}

