package com.example.facebook_integration.service.Implementations;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.repository.UserRepository;
import com.example.facebook_integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    // Method to create a new user in the database
    @Override
    public String createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        userRepository.save(user);
        return "User created successfully";
    }

    public int login(String email, String password){
        User  user =userRepository.findUserByEmail(email);
        if (user == null){
            return -1;
        }
        else if (user.getPassword().equals(password)) {
            return user.getId();
        }
        else {
            return -2;
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }
}
