package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.Friend;
import com.example.facebook_integration.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/{userId}/follow/{friendId}")
    public Friend followUser(@PathVariable int userId, @PathVariable int friendId) {
        return friendService.followUser(userId, friendId);
    }

    @PostMapping("/{userId}/unfollow/{friendId}")
    public void unfollowUser(@PathVariable int userId, @PathVariable int friendId) {
        friendService.unfollowUser(userId, friendId);
    }

    @PostMapping("/{userId}/block/{blockedId}")
    public Friend blockUser(@PathVariable int userId, @PathVariable int blockedId) {
        return friendService.blockUser(userId, blockedId);
    }

    @PostMapping("/{userId}/unblock/{blockedId}")
    public Friend unblockUser(@PathVariable int userId, @PathVariable int blockedId) {
        return friendService.unblockUser(userId, blockedId);
    }

    @GetMapping("/{userId}/friends")
    public List<Friend> getFriends(@PathVariable int userId) {
        return friendService.getFriends(userId);
    }

    @GetMapping("/{userId}/blocked")
    public List<Friend> getBlockedUsers(@PathVariable int userId) {
        return friendService.getBlockedUsers(userId);
    }
}

