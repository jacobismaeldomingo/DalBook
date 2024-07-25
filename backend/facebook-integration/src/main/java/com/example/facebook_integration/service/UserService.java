package com.example.facebook_integration.service;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.model.UserGroup;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    int createUser(User user);
    Optional<User> findUserByEmail(String email);
    void updatePassword(String email, String newPassword);
    User login (String email, String password);
    void updateStatus(String email, String status);
    void updateUserProfile(String firstName, String lastName, String email, String bio, User.Status status, MultipartFile profilePicture);
    Optional<User> findUserById(int id);
    List<User> getAllUsers();
    User addUser(User user);
    void removeUser(int id, String adminRole);
    User updateUserRole(int id, String role, String adminRole);
    @Transactional
    void deactivateUser(int id, String adminRole);
    @Transactional
    void activateUser(int id, String adminRole);
    List<UserGroup> getAllGroups(int userId);

//    List<JoinRequest> getJoinRequests();
//    JoinRequest handleJoinRequest(Long id, String action);
}
