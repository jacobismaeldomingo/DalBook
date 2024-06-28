import React, { useState, useEffect } from "react";
import friendService from "../../services/FriendService";
import { Link } from "react-router-dom";
import "./FriendRequest.css";



const FriendRequest = () => {
  const [userId, setUserId] = useState(null);
  const [receiverEmail, setReceiverEmail] = useState("");

  useEffect(() => {
    const id = localStorage.getItem("userId");
    setUserId(id);
  }, []);

  const [errors, setErrors] = useState({});

  
  const handleSendRequest = async () => {
  
    try {
      const result = await friendService.sendFriendRequest(userId, receiverEmail);
      if (result) {
        console.log('Friend request sent:', result);
      }
    } catch (error) {
      if (error.result && error.result.status === 500) {
        setErrors({error: "Friend request already sent" });
      } else {
        console.error('Error in sending request:', error);
      }
    }
  };

  return (
    <div>
      <h2 style={{ padding: "10px 10px" }}>Add Friends</h2>
      <h4 style={{ padding: "10px 10px" }}>Current User ID: {userId} </h4>
      <div>
        <input
          type="text-id"
          className="friends-input"
          value={receiverEmail}
          onChange={(e) => setReceiverEmail(e.target.value)}
          placeholder="Enter Email to send request"
        />
        <button onClick={handleSendRequest} className="btn send-button">
          Send Friend Request
        </button>
      </div>
      <div className="links">
        <Link
          to="/friendRequestList"
          className="btn btn-success text-decoration-none"
        >
          Friend Request List
        </Link>
        <Link
          to="/friendsList"
          className="btn btn-success text-decoration-none"
        >
          Friends List
        </Link>
      </div>
    </div>
  );
};

export default FriendRequest;
