package com.example.facebook_integration.service;

import com.example.facebook_integration.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {
    String createUser(User user);
    public int login (String email, String password);
    public void updatePassword(String email, String newPassword);
    public int login (String email, String password);


}
