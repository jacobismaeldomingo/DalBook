package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.FriendRequest;
import com.example.facebook_integration.model.User;
import com.example.facebook_integration.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/friends")
public class FriendRequestController {

    @Autowired
    FriendRequestService friendRequestService;

    @PostMapping("/send")
    public FriendRequest sendRequest(@RequestParam int senderId, @RequestParam String receiverEmail) {
        return friendRequestService.sendRequestByEmail(senderId, receiverEmail);
    }

    @PostMapping("/accept")
    public FriendRequest acceptRequest(@RequestParam int requestId) {
        return friendRequestService.acceptRequest(requestId);
    }

    @GetMapping("/pending")
    public List<FriendRequest> getPendingRequests(@RequestParam int userId) {
        return friendRequestService.getPendingRequests(userId);
    }

    @GetMapping("/list")
    public List<User> getFriends(@RequestParam int userId) {
        return friendRequestService.getFriends(userId);
    }
}
