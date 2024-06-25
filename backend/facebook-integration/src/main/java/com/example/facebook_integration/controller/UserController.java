package com.example.facebook_integration.controller;


import com.example.facebook_integration.model.User;
import com.example.facebook_integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @CrossOrigin(origins= "*", allowedHeaders = "*")
    @RestController
    @RequestMapping("/users")
    public class UserController {

        @Autowired
        UserService UserService;

        @PostMapping("/save")
        public void saveUser(@RequestBody User user){
            UserService.createUser(user);
        }
    }
