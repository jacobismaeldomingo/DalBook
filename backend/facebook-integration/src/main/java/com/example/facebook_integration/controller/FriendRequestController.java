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

    /**
     * Function: sendRequest
     * Purpose: Sends a friend request from one user to another using their email.
     * Parameters: int senderId - ID of the user sending the request.
     *             String receiverEmail - Email of the user receiving the request.
     * Returns: FriendRequest - The saved friend request.
     */
    @PostMapping("/send")
    public FriendRequest sendRequest(@RequestParam int senderId, @RequestParam String receiverEmail) {
        if (receiverEmail.isEmpty()) {
            // Handle the case where the receiverEmail is empty
        }
        return friendRequestService.sendRequestByEmail(senderId, receiverEmail);
    }

    /**
     * Function: acceptRequest
     * Purpose: Accepts a pending friend request.
     * Parameters: int requestId - ID of the friend request to be accepted.
     * Returns: FriendRequest - The updated friend request.
     */
    @PostMapping("/accept")
    public FriendRequest acceptRequest(@RequestParam int requestId) {
        return friendRequestService.acceptRequest(requestId);
    }

    /**
     * Function: getPendingRequests
     * Purpose: Retrieves all pending friend requests for a user.
     * Parameters: int userId - ID of the user.
     * Returns: List<FriendRequest> - List of pending friend requests.
     */
    @GetMapping("/pending")
    public List<FriendRequest> getPendingRequests(@RequestParam int userId) {
        return friendRequestService.getPendingRequests(userId);
    }

    /**
     * Function: getFriends
     * Purpose: Retrieves all friends for a user.
     * Parameters: int userId - ID of the user.
     * Returns: List<User> - List of friends.
     */
    @GetMapping("/list")
    public List<User> getFriends(@RequestParam int userId) {
        return friendRequestService.getFriends(userId);
    }

    /**
     * Function: deleteFriend
     * Purpose: Deletes a friend from a user's friend list.
     * Parameters: int userId - ID of the user.
     *             int friendId - ID of the friend to be deleted.
     * Returns: void
     */
    @DeleteMapping("/delete")
    public void deleteFriend(@RequestParam int userId, @RequestParam int friendId) {
        friendRequestService.deleteFriend(userId, friendId);
    }
}
