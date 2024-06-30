package com.example.facebook_integration.service.Implementations;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.repository.UserRepository;
import com.example.facebook_integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public int createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        userRepository.save(user);
        return user.getId();
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

    @Override
    public void updateUserProfile(String firstName, String lastName, String email, String bio, User.Status status, MultipartFile profilePicture) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setBio(bio);
            user.setStatus(status);

            if (!profilePicture.isEmpty()) {
                try {
                    byte[] profilePicBytes = profilePicture.getBytes();
                    // Save profilePicBytes to a location and update user.setProfilePic(newPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
    }
}
