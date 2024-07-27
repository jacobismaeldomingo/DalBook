package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.model.UserGroup;
import com.example.facebook_integration.repository.GroupRepository;
import com.example.facebook_integration.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    GroupRepository groupRepository;

    /**
     * Function: to add user to Group
     * @param groupId id of group
     * @param userId id of user
     * @return UserGroup The Group
     */
    @PutMapping("/addUser")
    public UserGroup addUser(@RequestParam int groupId, @RequestParam int userId) {
        return  groupService.addUserToGroup(groupId, userId);
    }

    /**
     * Function to delete User From UserGroup
     * @param groupId id of the Group
     * @param userId id of the User
     * @return String confirmation of deletion.
     */
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam int groupId, @RequestParam int userId) {
        return groupService.deleteUser(groupId, userId);
    }

    /**
     * Function : for User to Leave Group
     * @param groupId id of Group
     * @param userId id of User
     */
    @PostMapping("/leaveGroup")
    public void leaveGroup(@RequestParam int groupId, @RequestParam int userId) {
        groupService.leaveGroup(groupId, userId);
    }

    /**
     * Function: To create a new UserGroup
     * @param userGroup userGroupObject containing Details
     * @return userGroup created Group
     */
    @PostMapping("/createGroup")
    public UserGroup createGroup(@RequestBody UserGroup userGroup) {
        return groupService.createGroup(userGroup);
    }

    /**
     * Function: To get all the users in a specified group
     * @param groupId id of the group
     * @return List<User> List containing the users
     */
    @GetMapping("/users/{groupId}")
    public List<User> getAllUsers(@PathVariable int groupId) {
        return groupService.getAllUsers(groupId);
    }

    /**
     * Function: To get a UserGroup
     * @param groupId id of the group we want to get
     * @return UserGroup The GroupObject
     */
    @GetMapping("/get/{groupId}")
    public UserGroup getGroup(@PathVariable int groupId) {
        Optional<UserGroup> group = groupRepository.findById(groupId);
        if (group.isEmpty()) {
            throw new RuntimeException("Group not found");
        }
        return group.get();
    }

    /**
     * Function: Get all Groups in the repository
     * @return List<UserGroup> List containing UserGroups
     */
    @GetMapping("/groups")
    public List<UserGroup> getGroups() {
        return groupRepository.findAll();
    }

    /**
     * Function: Check if user is Admin of a Group
     * @param groupId id of the Group
     * @param userId id of the user
     * @return ResponseEntity<?> Returns a success message
     */
    @GetMapping("/checkAdmin/{groupId}/{userId}")
    public ResponseEntity<?> checkIfGroupAdmin(@PathVariable int groupId, @PathVariable int userId) {
        boolean isAdmin = groupService.isGroupAdmin(groupId, userId);
        return ResponseEntity.ok().body(Collections.singletonMap("isAdmin", isAdmin));
    }

    /**
     * Function : To Delete a Group
     * @param groupId id of the Group
     * @return String Confirmation of the deleted Group
     */
    @DeleteMapping("/deleteGroup/{groupId}")
    public String deleteGroup(@PathVariable int groupId) {
        Optional<UserGroup> group = groupRepository.findById(groupId);
        if (group.isEmpty()) {
            throw new RuntimeException("Group not found");
        }
        groupRepository.deleteById(groupId);
        return "Group " + group.get().getGroupName() + " Id: "+ group.get().getId()+ " deleted";
    }

    /**
     * Allows a user to join a Group
     * @param request map containing the user id and Group id
     * @return
     */
    @PutMapping("/join")
    public ResponseEntity<?> joinGroup(@RequestBody Map<String, Integer> request) {
        int groupId = request.get("groupId");
        int userId = request.get("userId");
        groupService.joinGroup(groupId, userId);
        return ResponseEntity.ok().build();
    }
}

