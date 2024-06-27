import React, { useState } from "react";
import friendService from "../../services/FriendService";
import "./FriendRequest.css";

const FriendRequestSender = ({ userId }) => {
  const [receiverId, setReceiverId] = useState("");

  const handleSendRequest = () => {
    friendService
      .sendFriendRequest(userId, receiverId)
      .then((response) => {
        console.log("Friend request sent:", response.data);
        alert("Friend request sent successfully!");
      })
      .catch((error) => {
        console.error("Error sending friend request:", error);
        alert("Error sending friend request, please try again.");
      });
  };

  return (
    <div>
      <h3 style={{ padding: "10px 10px" }}>Add Friends</h3>
      <input
        type="text"
        className="friends-input"
        value={receiverId}
        onChange={(e) => setReceiverId(e.target.value)}
        placeholder="Enter User ID to send request"
      />
      <button onClick={handleSendRequest} className="btn send-button">
        Send Friend Request
      </button>
    </div>
  );
};

export default FriendRequestSender;
