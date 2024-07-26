import React, { useEffect, useState } from "react";
import friendService from "../../services/FriendService";
import "./FriendRequest.css";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const FriendsList = () => {
  const [friends, setFriends] = useState([]);
  const [userId, setUserId] = useState(null);
  const navigate = useNavigate();

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
          toast.warn("An error occurred. Please try again!");
        });
    }
  }, []);

  const handleDelete = (event, friendId) => {
    event.stopPropagation(); // Prevent event propagation
    friendService
      .deleteFriend(userId, friendId)
      .then(() => {
        setFriends((prevFriends) =>
          prevFriends.filter((friend) => friend.id !== friendId)
        );
        toast.success("Successfully deleting them as your friend.");
      })
      .catch((error) => {
        console.error("Error deleting friend:", error);
        toast.warn("An error occurred. Please try again!");
      });
  };

  const viewFriendProfile = (friendEmail) => {
    navigate(`/friendProfile/${friendEmail}`);
  };

  return (
    <div>
      <h2 style={{ padding: "1.5rem", paddingBottom: "0" }}>Your Friends</h2>
      {/* <h4 style={{ padding: "1.5rem", paddingBottom: "1rem" }}>
        Current User ID: {userId}{" "}
      </h4> */}
      <div className="friends-list">
        {friends.map((friend) => (
          <div
            key={friend.id}
            className="friends-name"
            onClick={() => viewFriendProfile(friend.email)}
            style={{ cursor: "pointer" }}
          >
            <img
              src={
                friend.profilePic
                  ? `http://localhost:8085${friend.profilePic}`
                  : "/images/dalhousie-logo.png"
              }
              alt=""
              style={{ padding: "1rem" }}
            />
            <div className="request-name">
              {friend.firstName + " " + friend.lastName}
            </div>
            <div>
              <button
                className="btn delete-button"
                onClick={(event) => handleDelete(event, friend.id)}
              >
                Delete
              </button>
            </div>
          </div>
          // <li key={friend.id} className="friends-name">
          //   <div className="request-name">
          //     {friend.firstName + " " + friend.lastName}
          //   </div>
          //   <button
          //     className="btn delete-button"
          //     onClick={() => handleDelete(friend.id)}
          //   >
          //     Delete
          //   </button>
          // </li>
        ))}
      </div>
    </div>
  );
};

export default FriendsList;
