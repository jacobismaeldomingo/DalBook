import React, { useEffect, useState } from "react";
import friendService from "../../services/FriendService";
import { Link } from "react-router-dom";
import "./FriendRequest.css";
import NotificationComponent from '../notifications/Notifications.js';

const FriendRequestList = () => {
  const [requests, setRequests] = useState([]);
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    const storedUserId = localStorage.getItem("userId");
    if (storedUserId) {
      setUserId(storedUserId);
      friendService
        .getPendingRequests(storedUserId)
        .then((response) => {
          setRequests(response.data);
        })
        .catch((error) => {
          console.error("Error fetching pending requests:", error);
          alert("An error occurred. Please try again!");
        });
    }
  }, []);

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
      <h2 style={{ padding: "1.5rem", paddingBottom: "0" }}>Pending Friend Requests</h2>
      <h4 style={{ padding: "1.5rem", paddingBottom: "1rem" }}>Current User ID: {userId} </h4>
      <NotificationComponent />
      <ul>
        {requests.map((request) => (
          <li key={request.id}>
            <div className="request-name">
              {request.sender.firstName + " " + request.sender.lastName}
            </div>
            <button
              onClick={() => handleAcceptRequest(request.id)}
              className="btn btn-success"
            >
              Accept Request
            </button>
          </li>
        ))}
      </ul>
      <div className="friends-links">
        <Link to="/home" className="btn btn-primary text-decoration-none">
          Homepage
        </Link>
        <Link
          to="/friendRequest"
          className="btn btn-primary text-decoration-none"
        >
          Friend Request
        </Link>
        <Link
          to="/friendsList"
          className="btn btn-primary text-decoration-none"
        >
          Friends List
        </Link>
      </div>
    </div>
  );
};

export default FriendRequestList;
