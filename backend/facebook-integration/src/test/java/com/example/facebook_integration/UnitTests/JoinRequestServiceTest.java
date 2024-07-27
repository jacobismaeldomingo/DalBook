package com.example.facebook_integration.UnitTests;

import com.example.facebook_integration.model.JoinRequest;
import com.example.facebook_integration.model.User;
import com.example.facebook_integration.model.UserGroup;
import com.example.facebook_integration.repository.JoinRequestRepository;
import com.example.facebook_integration.service.GroupService;
import com.example.facebook_integration.service.Implementations.JoinRequestServiceImpl;
import com.example.facebook_integration.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JoinRequestServiceTest {

    @InjectMocks
    private JoinRequestServiceImpl joinRequestService;

    @Mock
    private JoinRequestRepository joinRequestRepository;

    @Mock
    private GroupService groupService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetJoinRequestsByGroup() {
        int groupId = 1;
        JoinRequest request1 = new JoinRequest();
        JoinRequest request2 = new JoinRequest();
        when(joinRequestRepository.findByGroupId(groupId)).thenReturn(List.of(request1, request2));

        List<JoinRequest> requests = joinRequestService.getJoinRequestsByGroup(groupId);

        assertEquals(2, requests.size());
        verify(joinRequestRepository).findByGroupId(groupId);
    }

    @Test
    public void testGetJoinRequestsByGroup_NoRequests() {
        int groupId = 1;
        when(joinRequestRepository.findByGroupId(groupId)).thenReturn(new ArrayList<>());

        List<JoinRequest> requests = joinRequestService.getJoinRequestsByGroup(groupId);

        assertTrue(requests.isEmpty());
        verify(joinRequestRepository).findByGroupId(groupId);
    }

    @Test
    public void testGetApprovedRequestsForUser() {
        int userId = 1;
        JoinRequest request1 = new JoinRequest();
        JoinRequest request2 = new JoinRequest();
        when(joinRequestRepository.findByUserIdAndStatus(userId, "APPROVED")).thenReturn(List.of(request1, request2));

        List<JoinRequest> requests = joinRequestService.getApprovedRequestsForUser(userId);

        assertEquals(2, requests.size());
        verify(joinRequestRepository).findByUserIdAndStatus(userId, "APPROVED");
    }

    @Test
    public void testGetApprovedRequestsForUser_NoApprovedRequests() {
        int userId = 1;
        when(joinRequestRepository.findByUserIdAndStatus(userId, "APPROVED")).thenReturn(new ArrayList<>());

        List<JoinRequest> requests = joinRequestService.getApprovedRequestsForUser(userId);

        assertTrue(requests.isEmpty());
        verify(joinRequestRepository).findByUserIdAndStatus(userId, "APPROVED");
    }

    @Test
    public void testApproveRequest_Success() {
        // Setup test data
        UserGroup group = new UserGroup();
        group.setId(1);
        group.setGroupName("Test Group");

        User user = new User();
        user.setId(1);
        user.setFirstName("John");

        JoinRequest request = new JoinRequest();
        request.setId(1);
        request.setUserId(1);
        request.setGroupId(1);
        request.setStatus("PENDING");

        when(joinRequestRepository.findById(1)).thenReturn(Optional.of(request));
        when(groupService.getGroupById(1)).thenReturn(Optional.of(group));
        when(userService.findUserById(1)).thenReturn(Optional.of(user));
        when(joinRequestRepository.save(any(JoinRequest.class))).thenReturn(request);

        // Call the method under test
        joinRequestService.approveRequest(1);

        // Verify interactions
        verify(groupService).addUserToGroup(1, 1);
        verify(joinRequestRepository).save(request);

        // Verify that the status has been updated
        assertEquals("APPROVED", request.getStatus());
    }

    @Test
    public void testApproveRequest_GroupNotFound() {
        int requestId = 1;
        JoinRequest request = new JoinRequest();
        request.setGroupId(1);
        when(joinRequestRepository.findById(requestId)).thenReturn(Optional.of(request));
        when(groupService.getGroupById(request.getGroupId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                joinRequestService.approveRequest(requestId));

        assertEquals("Group cannot be found", exception.getMessage());
    }

    @Test
    public void testRejectRequest_Success() {
        int requestId = 1;
        JoinRequest request = new JoinRequest();
        when(joinRequestRepository.findById(requestId)).thenReturn(Optional.of(request));

        joinRequestService.rejectRequest(requestId);

        assertEquals("REJECTED", request.getStatus());
        verify(joinRequestRepository).save(request);
    }

    @Test
    public void testRejectRequest_RequestNotFound() {
        int requestId = 1;
        when(joinRequestRepository.findById(requestId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                joinRequestService.rejectRequest(requestId));

        assertEquals("Request not found", exception.getMessage());
    }

    @Test
    public void testCreateRequest_Success() {
        int userId = 1;
        int groupId = 1;

        // Call the method under test
        joinRequestService.createRequest(userId, groupId);

        // Capture the argument passed to joinRequestRepository.save()
        ArgumentCaptor<JoinRequest> requestCaptor = ArgumentCaptor.forClass(JoinRequest.class);
        verify(joinRequestRepository).save(requestCaptor.capture());

        JoinRequest capturedRequest = requestCaptor.getValue();
        assertEquals(userId, capturedRequest.getUserId());
        assertEquals(groupId, capturedRequest.getGroupId());
        assertEquals("PENDING", capturedRequest.getStatus());
    }
}
