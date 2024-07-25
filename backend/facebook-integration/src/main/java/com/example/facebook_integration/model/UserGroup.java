package com.example.facebook_integration.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String groupName;
    private String description;



    private String Faculty;

    @ManyToMany(targetEntity = User.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_user",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<User>();




    public UserGroup(){}


    public UserGroup(int id, String groupName, String description, String Faculty) {
        this.groupName = groupName;
        this.description = description;
        this.id = id;
        this.Faculty = Faculty;
    }


    public int getId() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String name) {
        this.groupName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }



    public void addUser(User user) {
        users.add(user);
    }

    public boolean hasUser(User user) {
        return users.contains(user);
    }
    public boolean removeUser(User user) {
        return users.remove(user);
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }





}
