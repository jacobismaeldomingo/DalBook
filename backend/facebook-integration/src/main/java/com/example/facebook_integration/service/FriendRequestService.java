package com.example.facebook_integration.service;

import com.example.facebook_integration.model.FriendRequest;
import com.example.facebook_integration.model.User;

import java.util.List;

public interface FriendRequestService {
    FriendRequest sendRequest(int senderId, int receiverId);

    FriendRequest sendRequestByEmail(int senderId, String receiverEmail);

    FriendRequest acceptRequest(int requestId);

    List<FriendRequest> getPendingRequests(int userId);

    List<User> getFriends(int userId);
    void deleteFriend(int userId, int friendId);
}
