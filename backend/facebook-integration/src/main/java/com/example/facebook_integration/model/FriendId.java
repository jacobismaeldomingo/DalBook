package com.example.facebook_integration.model;

//import javax.persistence.Embeddable;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FriendId implements Serializable {

    private int userId;
    private int friendId;

    public FriendId() {}

    public FriendId(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    // Getters and setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendId friendId1 = (FriendId) o;
        return Objects.equals(userId, friendId1.userId) &&
                Objects.equals(friendId, friendId1.friendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendId);
    }
}

