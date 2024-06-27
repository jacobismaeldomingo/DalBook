package com.example.facebook_integration.service;

import com.example.facebook_integration.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {




    public String createUser(User user);
    Optional<User> findUserByEmail(String email);
    public void updatePassword(String email, String newPassword);
    public int login (String email, String password);
    public void updateStatus(String email, String status);
    public void updateUserProfile(String email, String bio, MultipartFile profilePicture, MultipartFile backgroundImage);



}
