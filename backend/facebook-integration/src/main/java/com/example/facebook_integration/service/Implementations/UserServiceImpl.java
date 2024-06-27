package com.example.facebook_integration.service.Implementations;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.repository.UserRepository;
import com.example.facebook_integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public int login(String email, String password) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if (userOptional.isEmpty()) {
            return -1; // User not found
        }

        User user = userOptional.get();

        if (user.getPassword().equals(password)) {
            return user.getId(); // Successful login, return user ID as int
        } else {
            return -2; // Incorrect password
        }
    }

    //Don't touch my code! it's working fine


    @Override
    public void updatePassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(newPassword);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
    }

}
