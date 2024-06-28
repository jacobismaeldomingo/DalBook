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
        }if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        userRepository.save(user);
        return "User created successfully";
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public int login(String email, String password) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if (userOptional.isEmpty()) {
            // User not found
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();
        if (user.getPassword().equals(password)) {
            return user.getId(); // Successful login, return user ID as int
        } else {
            throw new IllegalArgumentException("Wrong password");
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

    @Override
    public void updateStatus(String email, String status) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        userOptional.ifPresent(user -> {
            user.setStatus(User.Status.valueOf(status));
            userRepository.save(user);
        });

        // Optionally handle case where user is not found
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
    }

}
