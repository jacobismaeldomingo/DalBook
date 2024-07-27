package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.JoinRequest;
import com.example.facebook_integration.service.JoinRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/join-requests")
public class JoinRequestController {

    @Autowired
    private JoinRequestService joinRequestService;

    /**
     * Retrieves the list of approved join requests for a specific user.
     *
     * @param userId the ID of the user
     * @return a ResponseEntity containing the list of approved join requests
     */
    @GetMapping("/approved/{userId}")
    public ResponseEntity<List<JoinRequest>> getApprovedRequests(@PathVariable("userId") int userId) {
        List<JoinRequest> requests = joinRequestService.getApprovedRequestsForUser(userId);
        return ResponseEntity.ok(requests);
    }
    /**
     * Retrieves the list of join requests for a specific group.
     *
     * @param groupId the ID of the group
     * @return a ResponseEntity containing the list of join requests
     */
    @GetMapping("/{groupId}")
    public ResponseEntity<List<JoinRequest>> getJoinRequests(@PathVariable int groupId) {
        List<JoinRequest> requests = joinRequestService.getJoinRequestsByGroup(groupId);
        return ResponseEntity.ok(requests);
    }
    /**
     * Approves a specific join request.
     *
     * @param requestId the ID of the join request to approve
     * @return a ResponseEntity with an HTTP status indicating the operation result
     */
    @PutMapping("/{requestId}/approve")
    public ResponseEntity<Void> approveRequest(@PathVariable int requestId) {
        joinRequestService.approveRequest(requestId);
        return ResponseEntity.ok().build();
    }
    /**
     * Rejects a specific join request.
     *
     * @param requestId the ID of the join request to reject
     * @return a ResponseEntity with an HTTP status indicating the operation result
     */
    @PutMapping("/{requestId}/reject")
    public ResponseEntity<Void> rejectRequest(@PathVariable int requestId) {
        joinRequestService.rejectRequest(requestId);
        return ResponseEntity.ok().build();
    }
    /**
     * Creates a new join request.
     *
     * @param request the JoinRequest request to create
     * @return a ResponseEntity with an HTTP status indicating the operation result
     */
    @PostMapping
    public ResponseEntity<Void> createRequest(@RequestBody JoinRequest request) {
        joinRequestService.createRequest(request.getUserId(), request.getGroupId());
        return ResponseEntity.ok().build();
    }
}
