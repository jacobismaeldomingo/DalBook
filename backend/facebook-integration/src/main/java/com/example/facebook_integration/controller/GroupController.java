package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.model.UserGroup;
import com.example.facebook_integration.repository.GroupRepository;
import com.example.facebook_integration.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/Group")
public class GroupController {

    @Autowired
    GroupService groupService;
    @Autowired
    GroupRepository groupRepository;

    @PutMapping("/addUser")
    public UserGroup addUser(@RequestParam int GroupId, @RequestParam int userId) {
        return  groupService.addUserToGroup(GroupId, userId);
    }
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam int GroupId, @RequestParam int userId) {
        return groupService.deleteUser(GroupId, userId);
    }

    @PostMapping("/createGroup")
    public UserGroup createGroup(@RequestBody UserGroup userGroup) {

        return groupService.createGroup(userGroup);
    }

    @GetMapping("/Users/{groupId}")
    public List<User> getAllUsers(@PathVariable int groupId) {
        return groupService.getAllUsers( groupId);
    }

    @GetMapping("/Groups/{groupId}")
    public UserGroup getGroup(@PathVariable int groupId) {
        Optional<UserGroup> group = groupRepository.findById(groupId);
        if (!group.isPresent()) {
            throw new RuntimeException("Group not found");
        }
        return group.get();
    }

    @GetMapping("/Groups")
    public List<UserGroup> getGroups() {
        List<UserGroup> GroupList =groupRepository.findAll();
        return GroupList;
    }


    @DeleteMapping("/deleteGroup/{groupId}")
    public String deleteGroup(@PathVariable int groupId) {
        Optional<UserGroup> group = groupRepository.findById(groupId);
        if (!group.isPresent()) {
            throw new RuntimeException("Group not found");
        }
        groupRepository.deleteById(groupId);
        return "Group " + group.get().getGroupName() + " Id: "+ group.get().getId()+ " deleted";
    }

}

