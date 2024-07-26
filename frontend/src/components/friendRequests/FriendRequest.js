import React, { useState, useEffect } from "react";
import friendService from "../../services/FriendService";
import "./FriendRequest.css";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import axios from "axios";

const FriendRequest = () => {
  const [userId, setUserId] = useState(null);
  const [userEmail, setUserEmail] = useState(""); // Store current user's email
  const [receiverEmail, setReceiverEmail] = useState("");
  const [message, setMessage] = useState("");
  const [searchQuery, setSearchQuery] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [allUsers, setAllUsers] = useState([]);

  useEffect(() => {
    const id = localStorage.getItem("userId");
    setUserId(id);

    // Fetch user details including email
    axios
      .get(`http://localhost:8085/api/user/getById/${id}`)
      .then((response) => {
        setUserEmail(response.data.email);
      })
      .catch((error) => {
        console.error("Error fetching user email:", error);
        toast.warn("Error fetching user email.");
      });

    // Fetch all users and groups
    axios
      .get("http://localhost:8085/api/user/users")
      .then((response) => {
        setAllUsers(response.data);
      })
      .catch((error) => {
        console.error("Error fetching users:", error);
        toast.warn("Error fetching users.");
      });
  }, []);

  const handleSearchChange = (e) => {
    const query = e.target.value;
    setMessage("");
    setSearchQuery(query);

    if (query.length > 0) {
      let userResults = [];

      userResults = allUsers.filter((user) =>
        user.email.toLowerCase().includes(query.toLowerCase())
      );

      setSearchResults([...userResults.map((user) => ({ ...user }))]);
    } else {
      setSearchResults([]);
    }
  };

  const handleClearSearch = () => {
    setSearchQuery("");
    setSearchResults([]);
  };

  const handleSelectEmail = (email) => {
    setReceiverEmail(email);
    setSearchQuery(email);
    setSearchResults([]);
  };

  const handleSendRequest = async () => {
    if (!receiverEmail) {
      setMessage("Please enter an email before proceeding");
      return;
    }

    if (receiverEmail === userEmail) {
      setMessage("You cannot send a friend request to yourself.");
      return;
    }

    try {
      const result = await friendService.sendFriendRequest(
        userId,
        receiverEmail
      );
      if (Object.keys(result).length > 0) {
        toast.success("Friend Request sent successfully");
      }
    } catch (error) {
      console.error("Error in sending request:", error);
      toast.error("Error in sending request.");
    }
  };

  return (
    <div>
      <h2 style={{ padding: "1.5rem", paddingBottom: "0" }}>Add Friends</h2>
      <div className="friends-input">
        <input
          type="text-id"
          value={searchQuery}
          onChange={handleSearchChange}
          placeholder="Search for users by email"
        />
        {searchQuery && (
            <button className="clear-button" onClick={handleClearSearch}>
              ×
            </button>
          )}
        <button onClick={handleSendRequest} className="btn send-button">
          Send Friend Request
        </button>
        {message && <p className="email-error-message">{message}</p>}
        {searchResults.length > 0 && (
          <ul className="friendReq-search-results">
            {searchResults.map((user) => (
              <li key={user.id} onClick={() => handleSelectEmail(user.email)}>
                {user.email}
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default FriendRequest;
