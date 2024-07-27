package com.example.facebook_integration.service.Implementations;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.model.UserGroup;
import com.example.facebook_integration.repository.GroupRepository;
import com.example.facebook_integration.repository.UserRepository;
import com.example.facebook_integration.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user group and saves it to the repository.
     * Assigns the Group_Admin role to the user who created the group.
     *
     * @param userGroup the user group to create
     * @return the created user group
     */
    @Override
    public UserGroup createGroup(UserGroup userGroup) {
        if (userGroup == null) {
            throw new IllegalArgumentException("Group cannot be null");
        }
        if (groupRepository.existsById(userGroup.getId())) {
            throw new IllegalArgumentException("Group already exists");
        }
        UserGroup savedUserGroup = groupRepository.save(userGroup);

        // Assign Group_Admin role to the user who created the group
        User creator = userRepository.findById(userGroup.getCreatorId()).orElseThrow(() ->
                new IllegalArgumentException("User cannot be found"));
        creator.getGroups().add(savedUserGroup);
        savedUserGroup.addUser(creator);
        userRepository.save(creator);

        savedUserGroup.addAdmin(creator);
        groupRepository.save(savedUserGroup);

        return savedUserGroup;
    }
    /**
     * Adds a user to an existing group.
     *
     * @param groupId the ID of the group
     * @param userId  the ID of the user to add
     * @return the updated user group
     */
    @Override
    public UserGroup addUserToGroup(int groupId, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User cannot be found"));
        UserGroup userGroup = groupRepository.findById(groupId).orElseThrow(() ->
                new IllegalArgumentException("Group cannot be found"));

        if (userGroup.getUsers().contains(user)) {
            throw new IllegalArgumentException("User already exists in the group");
        }

        user.getGroups().add(userGroup);
        userGroup.addUser(user);
        userRepository.save(user);
        return groupRepository.save(userGroup);
    }
    /**
     * Deletes a group if the requesting user has the Group_Admin role.
     *
     * @param groupId the ID of the group to delete
     * @param userId  the ID of the user making the request
     * @return a success message
     */
    @Override
    public String deleteGroup(int groupId, int userId) {
        UserGroup userGroup = groupRepository.findById(groupId).orElseThrow(() ->
                new IllegalArgumentException("Group cannot be found"));

        // Check if the requester has Group_Admin role
        User requester = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User cannot be found"));

        if (!userGroup.isAdmin(requester)) {
            throw new SecurityException("User does not have Group_Admin role");
        }

        groupRepository.delete(userGroup);
        return "Group successfully deleted";
    }
    /**
     * Removes a user from a group.
     *
     * @param groupId the ID of the group
     * @param userId  the ID of the user to remove
     * @return a success message
     */
    @Override
    public String deleteUser(int groupId, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User cannot be found"));
        UserGroup userGroup = groupRepository.findById(groupId).orElseThrow(() ->
                new IllegalArgumentException("Group cannot be found"));

        if (!userGroup.hasUser(user)) {
            throw new IllegalArgumentException("User is not in this group");
        }

        userGroup.removeUser(user);
        groupRepository.save(userGroup);
        return "User successfully removed";
    }
    /**
     * Retrieves all users in a group.
     *
     * @param groupId the ID of the group
     * @return a list of users in the group
     */
    @Override
    public List<User> getAllUsers(int groupId) {
        UserGroup userGroup = groupRepository.findById(groupId).orElseThrow(() ->
                new IllegalArgumentException("Group cannot be found"));
        return userGroup.getUsers();
    }
    /**
     * Checks if a user is an admin of a group.
     *
     * @param groupId the ID of the group
     * @param userId  the ID of the user
     * @return true if the user is an admin, false otherwise
     */
    @Override
    public boolean isGroupAdmin(int groupId, int userId) {
        UserGroup userGroup = groupRepository.findById(groupId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (userGroup != null && user != null) {
            return userGroup.isAdmin(user);
        }
        return false;
    }
    /**
     * Allows a user to join a group.
     *
     * @param groupId the ID of the group
     * @param userId  the ID of the user
     */
    @Override
    public void joinGroup(int groupId, int userId) {
        UserGroup group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        group.getUsers().add(user);
        groupRepository.save(group);
    }
    /**
     * Allows a user to leave a group.
     *
     * @param groupId the ID of the group
     * @param userId  the ID of the user
     */
    @Override
    public void leaveGroup(int groupId, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User cannot be found"));
        UserGroup userGroup = groupRepository.findById(groupId).orElseThrow(() ->
                new IllegalArgumentException("Group cannot be found"));

        if (!userGroup.hasUser(user)) {
            throw new IllegalArgumentException("User is not in this group");
        }

        userGroup.removeUser(user);
        groupRepository.save(userGroup);
    }
    /**
     * Retrieves a group by its ID.
     *
     * @param groupId the ID of the group
     * @return an Optional containing the group if found, or empty if not found
     */
    @Override
    public Optional<UserGroup> getGroupById(int groupId) {
        return groupRepository.findById(groupId);
    }
}
