import React, { useState, useEffect } from "react";
import friendService from "../../services/FriendService";
import { Link } from "react-router-dom";
import "./FriendRequest.css";

const FriendRequest = () => {
  const [userId, setUserId] = useState(null);
  const [receiverEmail, setReceiverEmail] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    const id = localStorage.getItem("userId");
    setUserId(id);
  }, []);

  const handleChangeEmail = (e) => {
    setReceiverEmail(e.target.value);
    setMessage(""); // Clear message when user starts typing again
  };
  const handleSendRequest = async () => {
    if(!receiverEmail){
      setMessage("Please enter an email before proceeding")
    }
  try {
    const result = await friendService.sendFriendRequest(userId, receiverEmail);
    if (Object.keys(result).length > 0) {
      alert("Friend Request sent successfully");
      console.log('Friend request sent:', result);
    }
  } catch (error) {
    console.error('Error in sending request:', error);
  }
};

const ChildComponent = ({ message }) => {
  setMessage(message);
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
          onChange={handleChangeEmail}
          // onChange={(e) => setReceiverEmail(e.target.value)}
          placeholder="Enter Email to send request"
        />
        <button onClick={handleSendRequest} className="btn send-button">
          Send Friend Request
        </button>
        {message && <p className="message">{message}</p>}
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
