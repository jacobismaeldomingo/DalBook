import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Post.css";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const Post = () => {
  const [postText, setPostText] = useState("");
  const [userId, setUserId] = useState(null);
  const [user, setUser] = useState(null);

  // Fetch user data
  useEffect(() => {
    const storedUserId = localStorage.getItem("userId");
    const storedUserEmail = localStorage.getItem("userEmail");
    if (storedUserId && storedUserEmail) {
      setUserId(storedUserId);

      // Get Current User Information
      const fetchUser = async () => {
        try {
          const response = await axios.get(
            `http://localhost:8085/api/user/getByEmail/${storedUserEmail}`
          );
          setUser(response.data);
        } catch (error) {
          console.log("Error fetching user", error);
          toast.warn("Error fetching user.");
        }
      };
      fetchUser();
    }
  }, [userId]);

  const handlePostSubmit = async () => {
    if (!postText.trim()) { // Check if postText is empty or contains only whitespace
      toast.error("Post cannot be empty.");
      return;
    }

    const formData = new FormData();
    formData.append("text", postText);

    try {
      await axios.post(
        `http://localhost:8085/api/posts/create/${userId}`,
        formData
      );
      toast.success("Successful creating your post.");
      setPostText("");
    } catch (error) {
      console.error("Error posting:", error);
      toast.error("Error posting.");
    }
  };

  return (
    <div className="post-container">
      <div className="post-header">
        <h3>Create Post</h3>
      </div>
      <div className="create-post-container">
        <div className="user-info">
          <img
            src={
              user?.profilePic
                ? `http://localhost:8085${user.profilePic}`
                : "/images/dalhousie-logo.png"
            }
            alt="Profile"
            className="profile-pic"
          />
          <span>{user ? user.firstName + " " + user.lastName : "User"}</span>
        </div>
        <textarea
          placeholder={`What's on your mind, ${
            user ? user.firstName : "User"
          }?`}
          value={postText}
          onChange={(e) => setPostText(e.target.value)}
          required
        />
        <div className="post-options">
          <button onClick={handlePostSubmit} className="post-button">
            Post
          </button>
        </div>
      </div>
    </div>
  );
};

export default Post;
