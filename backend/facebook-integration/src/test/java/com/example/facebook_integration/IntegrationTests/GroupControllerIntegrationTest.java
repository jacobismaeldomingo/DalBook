package com.example.facebook_integration.IntegrationTests;



import com.example.facebook_integration.model.User;
import com.example.facebook_integration.model.UserGroup;
import com.example.facebook_integration.repository.GroupRepository;
import com.example.facebook_integration.repository.UserRepository;
import com.example.facebook_integration.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroupControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    private RestTemplate restTemplate;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        baseUrl = "http://localhost:" + port + "/api/group";
        restTemplate = new RestTemplate();
        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testCreateGroup() {
        //Initialize User
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@dal.ca");
        user.setPassword("Password1!");
        user.setDateOfBirth("1990-01-01");
        user.setSecurityAnswer("Answer");
        user = userRepository.save(user);
        //Initialize UserGroup
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("Test Group");
        userGroup.setDescription("A test group");
        userGroup.setFaculty("Test Faculty");
        userGroup.setCreatorId(user.getId());
        // Send a request to create a UserGroup
        ResponseEntity<UserGroup> response = restTemplate.postForEntity(baseUrl + "/createGroup", userGroup, UserGroup.class);
        //assert it was created successfully
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Group", response.getBody().getGroupName());
        assertEquals("A test group", response.getBody().getDescription());
        assertEquals("Test Faculty", response.getBody().getFaculty());
    }

    @Test
    public void testAddUserToGroup() {
        //Initialize User
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.doe@dal.ca");
        user.setPassword("Password1!");
        user.setDateOfBirth("1991-01-01");
        user.setSecurityAnswer("Answer");
        user = userRepository.save(user);
        //Initialize Group
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("Test Group");
        userGroup.setDescription("A test group");
        userGroup.setFaculty("Test Faculty");
        userGroup.setCreatorId(user.getId());
        userGroup = groupRepository.save(userGroup);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);
       //Send a request to add user to Group
        ResponseEntity<UserGroup> response = restTemplate.exchange(baseUrl + "/addUser?groupId=" + userGroup.getId() + "&userId=" + user.getId(), HttpMethod.PUT, entity, UserGroup.class);
        //Check it was done successfully
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Group", response.getBody().getGroupName());
        assertEquals("Jane", response.getBody().getUsers().getFirst().getFirstName());
    }

    @Test
    public void testGetAllUsers() {
        //Initialize the users
        User user = new User();
        user.setFirstName("Mark");
        user.setLastName("Smith");
        user.setEmail("mark.smith@dal.ca");
        user.setPassword("Password1!");
        user.setDateOfBirth("1992-01-01");
        user.setSecurityAnswer("Answer");
        user = userRepository.save(user);

        User user2 = new User();
        user2.setFirstName("Matthew");
        user2.setLastName("Smith");
        user2.setEmail("Matthew@dal.ca");
        user2.setPassword("Password1!");
        user2.setDateOfBirth("1993-01-01");
        user2.setSecurityAnswer("Answer");
        user2 = userRepository.save(user2);


       //Initialize Group
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("Test Group");
        userGroup.setDescription("A test group");
        userGroup.setFaculty("Test Faculty");
        userGroup.setCreatorId(user.getId());

        userGroup= groupRepository.save(userGroup);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);
        // Send request to add the two users
         restTemplate.exchange(baseUrl + "/addUser?groupId=" + userGroup.getId() + "&userId=" + user.getId(), HttpMethod.PUT, entity, UserGroup.class);
        restTemplate.exchange(baseUrl + "/addUser?groupId=" + userGroup.getId() + "&userId=" + user2.getId(), HttpMethod.PUT, entity, UserGroup.class);


        //Send a request to get the list of users
        ResponseEntity<User[]> response = restTemplate.getForEntity(baseUrl + "/users/" + userGroup.getId(), User[].class);
        //Check that the list of users was gotten successfully
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
        assertEquals("Mark", response.getBody()[0].getFirstName());
        assertEquals("Matthew", response.getBody()[1].getFirstName());
    }

    @Test
    public void testDeleteUserFromGroup() {
        //Initialize the users

        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.doe@dal.ca");
        user.setPassword("Password1!");
        user.setDateOfBirth("1991-01-01");
        user.setSecurityAnswer("Answer");
        user = userRepository.save(user);
        //Initialize Group
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("Test Group");
        userGroup.setDescription("A test group");
        userGroup.setFaculty("Test Faculty");
        userGroup.setCreatorId(user.getId());
        userGroup = groupRepository.save(userGroup);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);
        // send request to add user to the group
        restTemplate.exchange(baseUrl + "/addUser?groupId=" + userGroup.getId() + "&userId=" + user.getId(), HttpMethod.PUT, entity, UserGroup.class);
        // send request to delete User From Group
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/deleteUser?groupId=" + userGroup.getId() + "&userId=" + user.getId(), HttpMethod.POST, entity, String.class);
        //Check it was done successfully
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User successfully removed", response.getBody());
    }

    @Test
    public void testDeleteGroup() {
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);
        // Send request to delete Group
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/deleteGroup/" + userGroup.getId(), HttpMethod.DELETE, entity, String.class);
       // Check it was that group that was deleted
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Group " + userGroup.getGroupName() + " Id: " + userGroup.getId() + " deleted", response.getBody());
    }
}
