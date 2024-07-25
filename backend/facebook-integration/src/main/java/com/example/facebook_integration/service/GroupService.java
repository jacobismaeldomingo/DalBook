package com.example.facebook_integration.service;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.model.UserGroup;

import java.util.List;

public interface GroupService {



    public String deleteUser(int GroupId,  int userId);
//public String updateGroup(int GroupId);

    public UserGroup addUserToGroup(int GroupId, int UserId);
    public UserGroup createGroup(UserGroup userGroup);
    public String deleteGroup( int groupId);
    public List<User> getAllUsers(int groupId);


}

