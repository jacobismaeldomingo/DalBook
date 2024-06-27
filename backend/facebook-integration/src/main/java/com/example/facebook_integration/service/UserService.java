package com.example.facebook_integration.service;

import com.example.facebook_integration.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {

     String createUser(User user);
     User findUserByEmail(String email);
     void updatePassword(String email, String newPassword);
     int login (String email, String password);

}
