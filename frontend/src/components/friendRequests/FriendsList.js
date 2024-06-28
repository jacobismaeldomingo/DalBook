import React, { useEffect, useState } from "react";
import friendService from "../../services/FriendService";
import { Link } from "react-router-dom";
import "./FriendRequest.css";

const FriendsList = () => {
  const [friends, setFriends] = useState([]);
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    const storedUserId = localStorage.getItem("userId");
    if (storedUserId) {
      setUserId(storedUserId);
      friendService
        .getFriends(storedUserId)
        .then((response) => {
          setFriends(response.data);
        })
        .catch((error) => {
          console.error("Error fetching friends:", error);
          alert("An error occurred. Please try again!");
        });
    }
  }, []);

  return (
    <div>
      <h2 style={{ padding: "10px 10px" }}>Your Friends</h2>
      <h4 style={{ padding: "10px 10px" }}>Current User ID: {userId} </h4>
      <ul className="friends-list">
        {friends.map((friend) => (
          <li key={friend.id} className="name">
            {friend.firstName + " " + friend.lastName}
          </li>
        ))}
      </ul>
      <div className="links">
        <Link to="/home" className="btn btn-success text-decoration-none">
          Homepage
        </Link>
        <Link
          to="/friendRequest"
          className="btn btn-success text-decoration-none"
        >
          Add Friends
        </Link>
        <Link
          to="/friendRequestList"
          className="btn btn-success text-decoration-none"
        >
          Friend Request List
        </Link>
      </div>
    </div>
  );
};

export default FriendsList;
