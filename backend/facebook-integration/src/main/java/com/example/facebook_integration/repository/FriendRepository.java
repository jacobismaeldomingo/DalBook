package com.example.facebook_integration.repository;

import com.example.facebook_integration.model.Friend;
import com.example.facebook_integration.model.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {
    List<Friend> findByUserId(int userId);
    List<Friend> findByFriendId(int friendId);
}
