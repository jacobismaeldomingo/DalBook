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

    /**
     * Function: createUser
     * Purpose: Creates a new user.
     * Parameters: User user - The user object containing user details.
     * Returns: int - The ID of the newly created user.
     */
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

    /**
     * Function: findUserByEmail
     * Purpose: Finds a user by their email.
     * Parameters: String email - The email of the user.
     * Returns: Optional<User> - The user object, if found.
     */
    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    /**
     * Function: login
     * Purpose: Logs a user in by checking their email and password.
     * Parameters: String email - The user's email.
     *             String password - The user's password.
     * Returns: int - The ID of the logged-in user.
     */
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

    /**
     * Function: updatePassword
     * Purpose: Updates the password of a user.
     * Parameters: String email - The email of the user.
     *             String newPassword - The new password.
     * Returns: void
     */
    @Override
    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
    }

    /**
     * Function: updateStatus
     * Purpose: Updates the status of a user.
     * Parameters: String email - The email of the user.
     *             String status - The new status.
     * Returns: void
     */
    @Override
    public void updateStatus(String email, String status){
        User user =userRepository.findUserByEmail(email);

        if(user!=null) {
            user.setStatus(User.Status.valueOf(status));
            userRepository.save(user);
        });
        // Optionally handle case where user is not found
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
    }

    /**
     * Function: updateUserProfile
     * Purpose: Updates the profile information of a user.
     * Parameters: String firstName - The user's first name.
     *             String lastName - The user's last name.
     *             String email - The user's email.
     *             String bio - The user's bio.
     *             User.Status status - The user's status.
     *             MultipartFile profilePicture - The user's profile picture.
     * Returns: void
     */
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
