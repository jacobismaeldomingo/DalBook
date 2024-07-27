package com.example.facebook_integration.IntegrationTests;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.model.UserGroup;
import com.example.facebook_integration.repository.GroupRepository;
import com.example.facebook_integration.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class GroupRepositoryIntegrationTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void findAll() {
        //Initialize Users
        User user = new User();
        user.setFirstName("Alice");
        user.setLastName("Brown");
        user.setEmail("alice.brown@dal.ca");
        user.setPassword("Password1!");
        user.setDateOfBirth("1994-01-01");
        user.setSecurityAnswer("Answer");
        user = userRepository.save(user);

        //Initialize Group
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("Test Group");
        userGroup.setDescription("A test group");
        userGroup.setFaculty("Test Faculty");
        userGroup.setCreatorId(user.getId());
        groupRepository.save(userGroup);
        // Check that the repository find All is working as intended
        List<UserGroup> userGroups = groupRepository.findAll();
        assertNotNull(userGroups);
        assertEquals(1, userGroups.size());
        assertEquals("Test Group", userGroups.get(0).getGroupName());

    }

    @Test
    public void findById(){
        //Initialize Group
        User user = new User();
        user.setFirstName("Alice");
        user.setLastName("Brown");
        user.setEmail("alice.brown@dal.ca");
        user.setPassword("Password1!");
        user.setDateOfBirth("1994-01-01");
        user.setSecurityAnswer("Answer");
        user = userRepository.save(user);
        //Initialize Group
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("Test Group");
        userGroup.setDescription("A test group");
        userGroup.setFaculty("Test Faculty");
        userGroup.setCreatorId(user.getId());
        groupRepository.save(userGroup);
        //Check that repository find By Id is working as it should
        UserGroup savedUserGroup = groupRepository.findById(userGroup.getId()).get();
        assertNotNull(savedUserGroup);
        assertEquals("Test Group", savedUserGroup.getGroupName());
        assertEquals("Test Faculty", savedUserGroup.getFaculty());

    }


}
