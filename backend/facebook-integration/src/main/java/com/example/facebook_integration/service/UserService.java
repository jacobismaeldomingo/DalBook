package com.example.facebook_integration.service;

import com.example.facebook_integration.model.User;

public interface UserService {
    String createUser(User user);

    public User findByEmail(String email);
    public void updatePassword(String email, String newPassword);

}
