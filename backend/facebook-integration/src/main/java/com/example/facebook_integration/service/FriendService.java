package com.example.facebook_integration.service;

import com.example.facebook_integration.model.Friend;

import java.util.List;

public interface FriendService {

    Friend followUser(int userId, int friendId);
    void unfollowUser(int userId, int friendId);
    Friend blockUser(int userId, int blockedId);
    Friend unblockUser(int userId, int blockedId);
    List<Friend> getFriends(int userId);
    List<Friend> getBlockedUsers(int userId);
}
