package com.example.facebook_integration.IntegrationTests;

import com.example.facebook_integration.model.JoinRequest;
import com.example.facebook_integration.model.User;
import com.example.facebook_integration.model.UserGroup;
import com.example.facebook_integration.repository.JoinRequestRepository;
import com.example.facebook_integration.repository.GroupRepository;
import com.example.facebook_integration.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JoinRequestControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JoinRequestRepository joinRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    public void setUp() {
        joinRequestRepository.deleteAll();
        userRepository.deleteAll();
        groupRepository.deleteAll();
    }

    // Helper methods to create test data
    private User createUser(int id, String email) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setSecurityAnswer("France");
        user.setDateOfBirth("02-02-2002");
        userRepository.save(user);
        return user;
    }

    private UserGroup createGroup(int id) {
        UserGroup group = new UserGroup();
        group.setId(id);
        group.setGroupName("Test Group");
        groupRepository.save(group);
        return group;
    }

    private JoinRequest createJoinRequest(int id, int userId, int groupId, String status) {
        JoinRequest request = new JoinRequest();
        request.setId(id);
        request.setUserId(userId);
        request.setGroupId(groupId);
        request.setStatus(status);
        joinRequestRepository.save(request);
        return request;
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void testGetApprovedRequests() throws Exception {
        User user = createUser(1, "testuser@dal.ca");
        createJoinRequest(1, 1, 1, "APPROVED");

        // When
        ResponseEntity<JoinRequest[]> response = restTemplate.getForEntity(
                createURLWithPort("/api/join-requests/approved/1"),
                JoinRequest[].class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JoinRequest[] requests = response.getBody();
        assertNotNull(requests);
        assertEquals(1, requests.length);
        assertEquals("APPROVED", requests[0].getStatus());
    }

    @Test
    public void testGetJoinRequests() throws Exception {
        User user = createUser(1, "testuser@dal.ca");
        UserGroup group = createGroup(1);
        createJoinRequest(1, 1, 1, "PENDING");

        // When
        ResponseEntity<JoinRequest[]> response = restTemplate.getForEntity(
                createURLWithPort("/api/join-requests/1"),
                JoinRequest[].class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JoinRequest[] requests = response.getBody();
        assertNotNull(requests);
        assertEquals(1, requests.length);
        assertEquals("PENDING", requests[0].getStatus());
    }

    @Test
    public void testApproveRequest() throws Exception {
        // Given
        User user = createUser(1, "testuser@dal.ca");
        UserGroup group = createGroup(1);
        JoinRequest request = createJoinRequest(1, 1, 1, "PENDING");

        // Ensure the group and user are correctly set up
        assertNotNull(userRepository.findById(1).orElse(null));
        assertNotNull(groupRepository.findById(1).orElse(null));

        // When
        ResponseEntity<Void> response = restTemplate.exchange(
                createURLWithPort("/api/join-requests/1/approve"),
                HttpMethod.PUT,
                null,
                Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JoinRequest updatedRequest = joinRequestRepository.findById(1).orElse(null);
        assertNotNull(updatedRequest);
        assertEquals("APPROVED", updatedRequest.getStatus());
    }

    @Test
    public void testRejectRequest() throws Exception {
        // Given
        User user = createUser(1, "testuser@dal.ca");
        UserGroup group = createGroup(1);
        JoinRequest request = createJoinRequest(1, 1, 1, "PENDING");

        // When
        ResponseEntity<Void> response = restTemplate.exchange(
                createURLWithPort("/api/join-requests/1/reject"),
                HttpMethod.PUT,
                null,
                Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JoinRequest updatedRequest = joinRequestRepository.findById(1).orElse(null);
        assertNotNull(updatedRequest);
        assertEquals("REJECTED", updatedRequest.getStatus());
    }

    @Test
    public void testCreateRequest() {
        // Given
        User user = createUser(1, "testuser@dal.ca");
        UserGroup group = createGroup(1);
        JoinRequest request = new JoinRequest();
        request.setUserId(1);
        request.setGroupId(1);
        request.setStatus("PENDING");

        // When
        ResponseEntity<Void> response = restTemplate.postForEntity(
                createURLWithPort("/api/join-requests"),
                request,
                Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the request was saved
        List<JoinRequest> requests = joinRequestRepository.findAll();
        assertEquals(1, requests.size());
        JoinRequest savedRequest = requests.get(0);
        assertNotNull(savedRequest);
        assertEquals("PENDING", savedRequest.getStatus());
    }
}

