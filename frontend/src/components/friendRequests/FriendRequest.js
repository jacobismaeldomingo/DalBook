import React, { useState, useEffect } from "react";
import friendService from "../../services/FriendService";
import "./FriendRequest.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

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
    if (!receiverEmail) {
      setMessage("Please enter an email before proceeding");
      return;
    }
    try {
      const result = await friendService.sendFriendRequest(
        userId,
        receiverEmail
      );
      if (Object.keys(result).length > 0) {
        toast.success("Friend Request sent successfully");
        console.log("Friend request sent:", result);
      }
    } catch (error) {
      console.error("Error in sending request:", error);
    }
  };

  return (
    <div>
      <ToastContainer />
      <h2 style={{ padding: "1.5rem", paddingBottom: "0" }}>Add Friends</h2>
      {/* <h4 style={{ padding: "1.5rem" }}>Current User ID: {userId} </h4> */}
      <div className="friends-input">
        <input
          type="text-id"
          value={receiverEmail}
          onChange={handleChangeEmail}
          // onChange={(e) => setReceiverEmail(e.target.value)}
          placeholder="Enter Email to send request"
        />
        <button onClick={handleSendRequest} className="btn send-button">
          Send Friend Request
        </button>
        {message && <p className="email-error-message">{message}</p>}
      </div>
    </div>
  );
};

export default FriendRequest;
