import React, { useState, useEffect } from "react";
import "./Post.css";
import axios from "axios";
import { IconPhoto, IconSend } from "@tabler/icons-react";

const Post = () => {
  const [postText, setPostText] = useState("");
  const [selectedFile, setSelectedFile] = useState(null);
  const [feeling, setFeeling] = useState("");
  const [posts, setPosts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    // const fetchPosts = async () => {
    //   try {
    //     const response = await axios.get("http://localhost:8085/api/posts");
    //     setPosts(response.data);
    //     setIsLoading(false);
    //   } catch (error) {
    //     console.error("Error fetching posts:", error);
    //     setIsLoading(false);
    //   }
    // };

    // fetchPosts();
  }, []);

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const handlePostSubmit = async () => {
    const formData = new FormData();
    formData.append("text", postText);
    if (selectedFile) {
      formData.append("file", selectedFile);
    }
    formData.append("feeling", feeling);

    try {
      const response = await axios.post(`http://localhost:8085/api/posts/create/${userId}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      console.log(response);
      setPosts((prevPosts) => [response.data, ...prevPosts]);
      setPostText("");
      setSelectedFile(null);
      setFeeling("");
    } catch (error) {
      console.error("Error posting:", error);
    }
  };

  return (
    <div className="post-container">
      <div className="create-post">
        <textarea
          placeholder="What's on your mind?"
          value={postText}
          onChange={(e) => setPostText(e.target.value)}
        />
        <div className="post-options">
          <label htmlFor="file-input">
            <IconPhoto stroke={2} color="green" />
          </label>
          <input
            id="file-input"
            type="file"
            onChange={handleFileChange}
            style={{ display: "none" }}
          />
          <input
            type="text"
            placeholder="Feeling/Activity"
            value={feeling}
            onChange={(e) => setFeeling(e.target.value)}
            className="feeling-input"
          />
          <button onClick={handlePostSubmit} className="post-button">
            <IconSend stroke={2} color="blue" />
          </button>
        </div>
      </div>
      <div className="posts">
        {isLoading ? (
          "loading"
        ) : (
          posts.map((post) => (
            <div className="post" key={post.postId}>
              <p>{post.description}</p>
              {post.mediaUrl && (
                <img src={post.mediaUrl} alt="post" />
              )}
              <p>{post.feeling}</p>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default Post;
