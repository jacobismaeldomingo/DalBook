import React, { useEffect, useState } from "react";
import friendService from "../../services/FriendService";

const FriendRequestList = ({ userId }) => {
  const [requests, setRequests] = useState([]);

  useEffect(() => {
    friendService
      .getPendingRequests(userId)
      .then((response) => {
        setRequests(response.data);
      })
      .catch((error) => {
        console.error("Error fetching pending requests:", error);
        alert("An error occurred. Please try again!");
      });
  }, [userId]);

  const handleAcceptRequest = (requestId) => {
    friendService
      .acceptFriendRequest(requestId)
      .then((response) => {
        setRequests(requests.filter((req) => req.id !== requestId));
      })
      .catch((error) => {
        console.error("Error accepting friend request:", error);
      });
  };

  return (
    <div>
      <h3 style={{ padding: "10px 10px" }}>Pending Friend Requests</h3>
      <ul>
        {requests.map((request) => (
          <li key={request.id}>
            <div className="name">
              {request.sender.firstName + " " + request.sender.lastName}
            </div>
            <button
              onClick={() => handleAcceptRequest(request.id)}
              className="btn send-button"
            >
              Accept Request
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default FriendRequestList;
