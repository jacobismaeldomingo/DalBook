import React, { useState } from "react";
import "./Post.css";
import axios from "axios";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { IconPhoto, IconSend } from "@tabler/icons-react";

const Post = () => {
  const [postText, setPostText] = useState("");
  const [selectedFile, setSelectedFile] = useState(null);
  const [feeling, setFeeling] = useState("");
  const queryClient = useQueryClient();

  const { isLoading, data } = useQuery(["posts"], () =>
    axios.get("/api/posts").then((res) => res.data)
  );

  const mutation = useMutation(
    (newPost) => axios.post("/api/posts", newPost, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }),
    {
      onSuccess: () => {
        queryClient.invalidateQueries("posts");
      },
    }
  );

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const handlePostSubmit = () => {
    const formData = new FormData();
    formData.append("text", postText);
    if (selectedFile) {
      formData.append("file", selectedFile);
    }
    formData.append("feeling", feeling);

    mutation.mutate(formData);
    setPostText("");
    setSelectedFile(null);
    setFeeling("");
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
          data.map((post) => (
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
