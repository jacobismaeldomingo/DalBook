package com.example.facebook_integration.service.Implementations;

import com.example.facebook_integration.controller.UserController;
import com.example.facebook_integration.model.FriendRequest;
import com.example.facebook_integration.repository.FriendRequestRepository;
import com.example.facebook_integration.service.FriendRequestService;
import com.example.facebook_integration.model.User;
import com.example.facebook_integration.repository.UserRepository;
import com.example.facebook_integration.exception.FriendRequestAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());


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
        User receiver = userRepository.findUserByEmail(receiverEmail).orElseThrow(() -> new RuntimeException("Receiver not found with email: " + receiverEmail));

        // Check if already a friend or a friend request already exists
        int exists = statusBySenderAndReceiver(sender, receiver);
        if (exists == 0) {
            FriendRequest friendRequest = new FriendRequest(sender, receiver, false);
            logger.info(sender.getEmail() + "sent friend req to " + receiverEmail);
            return friendRequestRepository.save(friendRequest);
        }
        else if (exists == -1) {
            logger.info(sender.getEmail() + "has already sent friend req to " + receiverEmail);
            throw new FriendRequestAlreadyExistsException("Friend request already sent");
        }
        else if (exists == 1) {
            logger.info(sender.getEmail() + "is already friend with  " + receiverEmail);
            throw new FriendRequestAlreadyExistsException("Already friends");
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

    public int statusBySenderAndReceiver(User sender, User receiver){
        List<FriendRequest> requestsIfExists = friendRequestRepository.findBySenderAndReceiver(sender, receiver);
        if (requestsIfExists.isEmpty()) {
            return 0; // No friend request exists
        } else if (!requestsIfExists.getFirst().isAccepted()) {
            return -1; // Friend request already sent but not accepted
        } else {
            return 1; // Already friends
        }
    }
}
