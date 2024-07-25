package com.example.facebook_integration.service.Implementations;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.model.UserGroup;
import com.example.facebook_integration.repository.GroupRepository;
import com.example.facebook_integration.repository.UserRepository;
import com.example.facebook_integration.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserGroup createGroup(UserGroup userGroup) {
        if(userGroup ==null){
            throw new IllegalArgumentException("Group cannot be null");
        }
        if(groupRepository.existsById(userGroup.getId())){
            throw new IllegalArgumentException("Group already exists");
        }
        UserGroup savedUserGroup = groupRepository.save(userGroup);
        System.out.println(userGroup.getGroupName()+ " Printed");

        return savedUserGroup;
    }

    @Override
    public UserGroup addUserToGroup(int GroupId, int UserId) {
        User user = (userRepository.findById(UserId).orElseThrow(()->
                new IllegalArgumentException("User cannot be found")));
        UserGroup userGroup = groupRepository.findById(GroupId).orElseThrow(()->
                new IllegalArgumentException("Group cannot be not found"));
        if(userGroup ==null){
            throw new IllegalArgumentException("Group cannot be null");
        }
        if(user==null){
            throw new IllegalArgumentException("User cannot be null");
        }
        if(userGroup.getUsers().contains(user)){
            throw new IllegalArgumentException("User already exists");
        }
        user.getGroups().add(userGroup);
        userGroup.addUser(user);
        userRepository.save(user);
        UserGroup savedUserGroup = groupRepository.save(userGroup);
        return savedUserGroup;
    }

    @Override
    public String deleteGroup(int GroupId){
        UserGroup userGroup =groupRepository.findByID(GroupId);
        if(groupRepository.existsById( GroupId)){
            groupRepository.delete(userGroup);
            return "group successfully deleted";
        }
        else {
            return "group does not exist";
        }

    }

    @Override
    public String deleteUser(int GroupId, int UserId){
        User  user = (userRepository.findById(UserId).orElseThrow(()->
                new IllegalArgumentException("User cannot be found")));
        UserGroup userGroup = groupRepository.findById(GroupId).orElseThrow(()->
                new IllegalArgumentException("Group cannot be found"));
        if(userGroup ==null){
            throw new IllegalArgumentException("Group cannot be null");
        }
        if(user==null){
            throw new IllegalArgumentException("User cannot be null");
        }
        if(!userGroup.hasUser(user)){
            throw new IllegalArgumentException("User is not in this group");
        }
        else {
            userGroup.removeUser(user);
            groupRepository.save(userGroup);
            return "User successfully removed";
        }
    }

    @Override
    public List<User> getAllUsers(int GroupId) {
        UserGroup userGroup = groupRepository.findById(GroupId).orElseThrow();
        return userGroup.getUsers();
    }
}

