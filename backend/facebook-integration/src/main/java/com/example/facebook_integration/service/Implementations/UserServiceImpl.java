package com.example.facebook_integration.service.Implementations;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.model.UserGroup;
import com.example.facebook_integration.repository.FriendRequestRepository;
import com.example.facebook_integration.repository.UserRepository;
import com.example.facebook_integration.service.UserService;
import jakarta.transaction.Transactional;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private FriendRequestRepository friendRequestRepository;

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
        }
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
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
    public User login(String email, String password) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if (userOptional.isEmpty()) {
            // User not found
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();
        if (!user.getIsActive()) {
            throw new IllegalArgumentException("Your account has been deactivated");
        }

        if (user.getPassword().equals(password)) {
            return user; // Successful login, return user ID as int
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
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
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
                    String profilePicDir = "backend/facebook-integration/src/main/resources/static/profile_pictures/";
                    // Create the absolute path to the profile pictures directory
                    Path profilePicPath = Paths.get(profilePicDir).toAbsolutePath().normalize();
                    // Ensure the directories exist
                    // Files.createDirectories(profilePicPath);

                    // Get the path to the specific file
                    Path filePath = profilePicPath.resolve(Objects.requireNonNull(profilePicture.getOriginalFilename()));
                    // Write the file
                    Files.write(filePath, profilePicture.getBytes());

                    // Set the URL for the user profile picture
                    user.setProfilePic("/profile_pictures/" + profilePicture.getOriginalFilename());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
    }

    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .filter(user -> !user.getRole().toString().equals("System_Admin"))
                .collect(Collectors.toList());
    }

    @Override
    public User addUser(User user) {
        if (!"System_Admin".equals(user.getRole().toString())) {
            throw new SecurityException("Unauthorized");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUserRole(int id, String role, String adminRole) {
        if (!"System_Admin".equals(adminRole)) {
            throw new SecurityException("Unauthorized");
        }

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole(User.Role.valueOf(role)); // Assuming Role is an enum
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public void deactivateUser(int id, String adminRole) {
        if (!"System_Admin".equals(adminRole)) {
            throw new SecurityException("Unauthorized");
        }

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(false);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public void activateUser(int id, String adminRole) {
        if (!"System_Admin".equals(adminRole)) {
            throw new SecurityException("Unauthorized");
        }

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(true);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    @Transactional
    public void removeUser(int id, String adminRole) {
        if (!"System_Admin".equals(adminRole)) {
            throw new SecurityException("Unauthorized");
        }

        Optional<User> userOptional = findUserById(id);

        if (userOptional.isPresent()) {
            // Delete all friend requests where the user is involved
            friendRequestRepository.deleteBySenderId(id);
            friendRequestRepository.deleteByReceiverId(id);

            // Delete the user
            userRepository.deleteById(id);
        }
        else {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
    }

    @Override
    public List<UserGroup> getAllGroups(int userId){
        User user = userRepository.findById(userId).get();
        return user.getGroups();
    }

    @Override
    public List<User> findUsersByName(String name) {
        return userRepository.findUsersByFirstName(name);
    }

//    @PreAuthorize("hasRole('FACULTY_ADMIN')")
//    @Override
//    public List<JoinRequest> getJoinRequests() {
//        return joinRequestRepository.findAll();
//    }
//
//    @PreAuthorize("hasRole('FACULTY_ADMIN')")
//    @Override
//    public JoinRequest handleJoinRequest(Long id, String action) {
//        Optional<JoinRequest> joinRequestOptional = joinRequestRepository.findById(id);
//        if (joinRequestOptional.isPresent()) {
//            JoinRequest joinRequest = joinRequestOptional.get();
//            if ("approve".equalsIgnoreCase(action)) {
//                joinRequest.setStatus("APPROVED");
//            } else if ("reject".equalsIgnoreCase(action)) {
//                joinRequest.setStatus("REJECTED");
//            } else {
//                throw new IllegalArgumentException("Invalid action");
//            }
//            return joinRequestRepository.save(joinRequest);
//        } else {
//            throw new IllegalArgumentException("Join request not found");
//        }
//    }
}
