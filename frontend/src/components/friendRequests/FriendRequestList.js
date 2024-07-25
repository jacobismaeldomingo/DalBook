import React, { useEffect, useState } from "react";
import friendService from "../../services/FriendService";
import "./FriendRequest.css";
import NotificationComponent from '../notifications/Notifications.js';
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const FriendRequestList = () => {
  const [requests, setRequests] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const storedUserId = localStorage.getItem("userId");
    if (storedUserId) {
      friendService
        .getPendingRequests(storedUserId)
        .then((response) => {
          setRequests(response.data);
        })
        .catch((error) => {
          console.error("Error fetching pending requests:", error);
          toast.warn("An error occurred. Please try again!");
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

  const viewFriendProfile = (friendEmail) => {
    navigate(`/friendProfile/${friendEmail}`);
  };
  
  return (
    <div>
      <ToastContainer />
      <NotificationComponent />
      <h2 style={{ padding: "1.5rem", paddingBottom: "0" }}>Pending Friend Requests</h2>
      {/* <h4 style={{ padding: "1.5rem", paddingBottom: "1rem" }}>Current User ID: {userId} </h4> */}
      <div className="friends-list">
        {requests.map((request) => (
          <div
          key={request.id}
          className="friends-name"
          onClick={() => viewFriendProfile(request.sender.email)}
          style={{ cursor: "pointer" }}
        >
          <img
            src={
              request.sender.profilePic
                ? `http://localhost:8085${request.sender.profilePic}`
                : "/images/dalhousie-logo.png"
            }
            alt=""
            style={{ padding: "1rem" }}
          />
          <div className="request-name">
            {request.sender.firstName + " " + request.sender.lastName}
          </div>
          <div>
          <button
              onClick={() => handleAcceptRequest(request.id)}
              className="btn btn-success"
            >
              Accept Request
            </button>
          </div>
        </div>
          // <li key={request.id}>
          //   <div className="request-name">
          //     {request.sender.firstName + " " + request.sender.lastName}
          //   </div>
          //   <button
          //     onClick={() => handleAcceptRequest(request.id)}
          //     className="btn btn-success"
          //   >
          //     Accept Request
          //   </button>
          // </li>
        ))}
      </div>
    </div>
  );
};

export default FriendRequestList;
