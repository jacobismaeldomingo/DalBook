// CategoryOfTheDay.js
import React, { useState, useEffect } from "react";
import axios from "axios";
import "./CategoryOfDay.css";
import Header from "./Header";

const CategoryOfTheDay = () => {
  const [content, setContent] = useState("");
  const [topic, setTopic] = useState("");

  useEffect(() => {
    const fetchTopic = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8085/api/topics/latest"
        );
        setTopic(response.data.topic);
      } catch (error) {
        console.error("Error fetching topic:", error);
      }
    };

    fetchTopic();
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const userId = localStorage.getItem("userId");
    const post = { userId, topic, content };

    try {
      const response = await axios.post(
        "http://localhost:8085/api/category/posts",
        post
      );
      console.log("Post created:", response.data);
      alert("Post created successfully!");
    } catch (error) {
      console.error("Error creating post:", error);
      alert("An error occurred. Please try again!");
    }
  };

  return (
    <div>
      <Header />
      <form className="category-form" onSubmit={handleSubmit}>
        <div>
          <label className="category-label">Topic:</label>
          <h2 className="category-title">
            Today's category is about '{topic}'. Tell us what you think.
          </h2>
        </div>
        <div>
          <label className="category-label" style={{ paddingTop: "20px" }}>
            Post Content:
          </label>
          <textarea
            className="category-textarea"
            value={content}
            placeholder="Post your moment here..."
            onChange={(e) => setContent(e.target.value)}
            rows="10"
            required
          ></textarea>
        </div>
        <button className="category-button" type="submit">
          Submit
        </button>
      </form>
    </div>
  );
};

export default CategoryOfTheDay;
