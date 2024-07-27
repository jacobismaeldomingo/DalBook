package com.example.facebook_integration.service.Implementations;

import com.example.facebook_integration.model.Friend;
import com.example.facebook_integration.model.FriendId;
import com.example.facebook_integration.model.User;
import com.example.facebook_integration.repository.FriendRepository;
import com.example.facebook_integration.repository.UserRepository;
import com.example.facebook_integration.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    public Friend followUser(int userId, int friendId) {
        User user = userRepository.findById(userId).orElseThrow();
        User friend = userRepository.findById(friendId).orElseThrow();
        Friend newFriend = new Friend(user, friend);
        return friendRepository.save(newFriend);
    }

    public void unfollowUser(int userId, int friendId) {
        FriendId friendIdObj = new FriendId(userId, friendId);
        friendRepository.deleteById(friendIdObj);
    }

    public Friend blockUser(int userId, int blockedId) {
        FriendId friendIdObj = new FriendId(userId, blockedId);
        Friend friend = friendRepository.findById(friendIdObj).orElseThrow();
        friend.setBlocked(true);
        return friendRepository.save(friend);
    }

    public Friend unblockUser(int userId, int blockedId) {
        FriendId friendIdObj = new FriendId(userId, blockedId);
        Friend friend = friendRepository.findById(friendIdObj).orElseThrow();
        friend.setBlocked(false);
        return friendRepository.save(friend);
    }

    public List<Friend> getFriends(int userId) {
        return friendRepository.findByUserId(userId);
    }

    public List<Friend> getBlockedUsers(int userId) {
        return friendRepository.findByUserId(userId).stream()
                .filter(Friend::isBlocked)
                .toList();
    }
}
