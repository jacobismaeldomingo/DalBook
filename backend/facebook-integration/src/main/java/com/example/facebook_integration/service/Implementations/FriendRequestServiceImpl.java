package com.example.facebook_integration.service.Implementations;

import com.example.facebook_integration.model.FriendRequest;
import com.example.facebook_integration.repository.FriendRequestRepository;
import com.example.facebook_integration.service.FriendRequestService;
import com.example.facebook_integration.model.User;
import com.example.facebook_integration.repository.UserRepository;
import com.example.facebook_integration.exception.FriendRequestAlreadyExistsException;
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

//    @Override
//    public FriendRequest sendRequestByEmail(int senderId, String receiverEmail) {
//        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
//        User receiver = userRepository.findUserByEmail(receiverEmail).orElseThrow(() -> new RuntimeException("Receiver not found with email: "+ receiverEmail));
//
//        // Check if a friend request already exists
//        boolean exists = existsBySenderAndReceiver(sender, receiver);
//        if (exists) {
//            throw new RuntimeException("Friend request already sent");
//        }
//
//        FriendRequest friendRequest = new FriendRequest(sender, receiver, false);
//        return friendRequestRepository.save(friendRequest);
//    }

    @Override
    public FriendRequest sendRequestByEmail(int senderId, String receiverEmail) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findUserByEmail(receiverEmail).orElseThrow(() -> new RuntimeException("Receiver not found with email: " + receiverEmail));

        // Check if already a friend or a friend request already exists
        int exists = statusBySenderAndReceiver(sender, receiver);
        if (exists == 0) {
            FriendRequest friendRequest = new FriendRequest(sender, receiver, false);
            return friendRequestRepository.save(friendRequest);
        }
        else if (exists == 1) {
            throw new FriendRequestAlreadyExistsException("Already a friend");
        }
        else if (exists == -1) {
            throw new FriendRequestAlreadyExistsException("Friend request already sent");
        }
        else {
            throw new FriendRequestAlreadyExistsException("Unknown server error check backend");
        }
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

    public boolean existsBySenderAndReceiver(User sender, User receiver){
        List<FriendRequest> requestsIfExists = friendRequestRepository.findBySenderAndReceiver(sender, receiver);
        return !requestsIfExists.isEmpty();
    }
    @Override
    public void deleteFriend(int userId, int friendId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User friend = userRepository.findById(friendId).orElseThrow(() -> new RuntimeException("Friend not found"));

        List<FriendRequest> requestsAsSender = friendRequestRepository.findBySenderAndReceiver(user, friend);
        List<FriendRequest> requestsAsReceiver = friendRequestRepository.findBySenderAndReceiver(friend, user);

        requestsAsSender.forEach(friendRequestRepository::delete);
        requestsAsReceiver.forEach(friendRequestRepository::delete);
    }

    public int statusBySenderAndReceiver(User sender, User receiver){
        List<FriendRequest> requestsIfExists = friendRequestRepository.findBySenderAndReceiver(sender, receiver);
        if(!requestsIfExists.isEmpty()){
            return -1;
        } else if (!requestsIfExists.getFirst().isAccepted()) {
            return 1;
        }
        else return 0;
    }
}
