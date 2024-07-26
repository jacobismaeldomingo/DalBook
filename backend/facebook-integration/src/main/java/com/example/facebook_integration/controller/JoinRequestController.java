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

    @GetMapping("/approved/{userId}")
    public ResponseEntity<List<JoinRequest>> getApprovedRequests(@PathVariable("userId") int userId) {
        List<JoinRequest> requests = joinRequestService.getApprovedRequestsForUser(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<List<JoinRequest>> getJoinRequests(@PathVariable int groupId) {
        List<JoinRequest> requests = joinRequestService.getJoinRequestsByGroup(groupId);
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{requestId}/approve")
    public ResponseEntity<Void> approveRequest(@PathVariable int requestId) {
        joinRequestService.approveRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<Void> rejectRequest(@PathVariable int requestId) {
        joinRequestService.rejectRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createRequest(@RequestBody JoinRequest request) {
        joinRequestService.createRequest(request.getUserId(), request.getGroupId());
        return ResponseEntity.ok().build();
    }
}

