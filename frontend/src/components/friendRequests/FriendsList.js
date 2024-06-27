import React, { useEffect, useState } from "react";
import friendService from "../../services/FriendService";

const FriendsList = ({ userId }) => {
  const [friends, setFriends] = useState([]);

  useEffect(() => {
    friendService
      .getFriends(userId)
      .then((response) => {
        setFriends(response.data);
      })
      .catch((error) => {
        console.error("Error fetching friends:", error);
        alert("An error occurred. Please try again!");
      });
  }, [userId]);

  return (
    <div>
      <h3 style={{ padding: "10px 10px" }}>Your Friends</h3>
      <ul>
        {friends.map((friend) => (
          <li key={friend.id} className="name">
            {friend.firstName + " " + friend.lastName}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default FriendsList;
