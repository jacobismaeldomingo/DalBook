package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable int id, @RequestParam String role, @RequestParam String adminRole) {
        return ResponseEntity.ok(userService.updateUserRole(id, role, adminRole));
    }

    @PutMapping("/users/deactivate/{id}")
    public ResponseEntity<Void> deactivateUser(@PathVariable int id, @RequestBody Map<String, String> requestBody) {
        String adminRole = requestBody.get("adminRole");
        userService.deactivateUser(id, adminRole);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/activate/{id}")
    public ResponseEntity<Void> activateUser(@PathVariable int id, @RequestBody Map<String, String> requestBody) {
        String adminRole = requestBody.get("adminRole");
        userService.activateUser(id, adminRole);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/remove/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable int id, @RequestParam String adminRole) {
        userService.removeUser(id, adminRole);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/users")
//    public ResponseEntity<User> addUser(@RequestBody User user, @RequestBody User.Role role) {
//        return ResponseEntity.ok(userService.addUser(user, String.valueOf(role)));
//    }
//
//    @GetMapping("/join-requests")
//    public ResponseEntity<List<JoinRequest>> getJoinRequests() {
//        return ResponseEntity.ok(userService.getJoinRequests());
//    }
//
//    @PutMapping("/join-requests/{id}")
//    public ResponseEntity<JoinRequest> handleJoinRequest(@PathVariable Long id, @RequestBody String action) {
//        return ResponseEntity.ok(userService.handleJoinRequest(id, action));
//    }
}
