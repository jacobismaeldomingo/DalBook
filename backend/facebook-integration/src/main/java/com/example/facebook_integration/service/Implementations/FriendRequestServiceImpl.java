package com.example.facebook_integration.service.Implementations;

import com.example.facebook_integration.model.FriendRequest;
import com.example.facebook_integration.repository.FriendRequestRepository;
import com.example.facebook_integration.service.FriendRequestService;
import com.example.facebook_integration.model.User;
import com.example.facebook_integration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public FriendRequest sendRequest(int senderId, int receiverId) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

        FriendRequest friendRequest = new FriendRequest(sender, receiver, false);
        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public FriendRequest sendRequestByEmail(int senderId, String receiverEmail) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findUserByEmail(receiverEmail).orElseThrow(() -> new RuntimeException("Receiver not found"));

        FriendRequest friendRequest = new FriendRequest(sender, receiver, false);
        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public FriendRequest acceptRequest(int requestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        friendRequest.setAccepted(true);
        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public List<FriendRequest> getPendingRequests(int userId) {
        User receiver = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return friendRequestRepository.findByReceiverAndAcceptedFalse(receiver);
    }

    @Override
    public List<User> getFriends(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<FriendRequest> sentRequests = friendRequestRepository.findBySenderAndAcceptedTrue(user);
        List<FriendRequest> receivedRequests = friendRequestRepository.findByReceiverAndAcceptedTrue(user);

        List<User> friends = new ArrayList<>();
        sentRequests.forEach(request -> friends.add(request.getReceiver()));
        receivedRequests.forEach(request -> friends.add(request.getSender()));
        return friends;
    }
}
