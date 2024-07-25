package com.example.facebook_integration.service;

import com.example.facebook_integration.model.User;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

public interface UserService {

    int createUser(User user);
    Optional<User> findUserByEmail(String email);
    void updatePassword(String email, String newPassword);
    int login (String email, String password);
    void updateStatus(String email, String status);
    void updateUserProfile(String firstName, String lastName, String email, String bio, User.Status status, MultipartFile profilePicture);
}
