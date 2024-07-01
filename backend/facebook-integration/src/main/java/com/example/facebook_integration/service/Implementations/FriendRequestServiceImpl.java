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

    /**
     * Function: sendRequest
     * Purpose: Sends a friend request from one user to another.
     * Parameters: int senderId - ID of the user sending the request.
     *             int receiverId - ID of the user receiving the request.
     * Returns: FriendRequest - The saved friend request.
     */
    @Override
    public FriendRequest sendRequest(int senderId, int receiverId) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

        FriendRequest friendRequest = new FriendRequest(sender, receiver, false);
        return friendRequestRepository.save(friendRequest);
    }

    /**
     * Function: sendRequestByEmail
     * Purpose: Sends a friend request from one user to another using their email.
     * Parameters: int senderId - ID of the user sending the request.
     *             String receiverEmail - Email of the user receiving the request.
     * Returns: FriendRequest - The saved friend request.
     */
    @Override
    public FriendRequest sendRequestByEmail(int senderId, String receiverEmail) {

        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findUserByEmail(receiverEmail).orElseThrow(() -> new RuntimeException("No user found with email: " + receiverEmail));

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

    /**
     * Function: acceptRequest
     * Purpose: Accepts a pending friend request.
     * Parameters: int requestId - ID of the friend request to be accepted.
     * Returns: FriendRequest - The updated friend request.
     */
    @Override
    public FriendRequest acceptRequest(int requestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        friendRequest.setAccepted(true);
        return friendRequestRepository.save(friendRequest);
    }

    /**
     * Function: getPendingRequests
     * Purpose: Retrieves all pending friend requests for a user.
     * Parameters: int userId - ID of the user.
     * Returns: List<FriendRequest> - List of pending friend requests.
     */
    @Override
    public List<FriendRequest> getPendingRequests(int userId) {
        User receiver = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return friendRequestRepository.findByReceiverAndAcceptedFalse(receiver);
    }

    /**
     * Function: getFriends
     * Purpose: Retrieves all friends for a user.
     * Parameters: int userId - ID of the user.
     * Returns: List<User> - List of friends.
     */
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

    /**
     * Function: existsBySenderAndReceiver
     * Purpose: Checks if a friend request exists between two users.
     * Parameters: User sender - The user sending the request.
     *             User receiver - The user receiving the request.
     * Returns: boolean - True if a friend request exists, otherwise false.
     */
    public boolean existsBySenderAndReceiver(User sender, User receiver){
        List<FriendRequest> requestsIfExists = friendRequestRepository.findBySenderAndReceiver(sender, receiver);
        return !requestsIfExists.isEmpty();
    }

    /**
     * Function: statusBySenderAndReceiver
     * Purpose: Checks the status of a friend request between two users.
     * Parameters: User sender - The user sending the request.
     *             User receiver - The user receiving the request.
     * Returns: int - 0 if no friend request exists, -1 if friend request is pending, 1 if already friends.
     */
    public int statusBySenderAndReceiver(User sender, User receiver) {
        List<FriendRequest> requestsIfExists = friendRequestRepository.findBySenderAndReceiver(sender, receiver);
        if (requestsIfExists.isEmpty()) {
            return 0; // No friend request exists
        } else if (!requestsIfExists.getFirst().isAccepted()) {
            return -1; // Friend request already sent but not accepted
        } else {
            return 1; // Already friends
        }
    }

    /**
     * Function: deleteFriend
     * Purpose: Deletes a friend from a user's friend list.
     * Parameters: int userId - ID of the user.
     *             int friendId - ID of the friend to be deleted.
     * Returns: void
     */
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
        if (requestsIfExists.isEmpty()) {
            return 0; // No friend request exists
        } else if (!requestsIfExists.getFirst().isAccepted()) {
            return -1; // Friend request already sent but not accepted
        } else {
            return 1; // Already friends
        }
    }
}
