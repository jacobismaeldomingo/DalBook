import React, { useState } from "react";
import axios from "axios";
import "./CategoryOfDay.css";
import Header from "./Header";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const CategoryAdmin = () => {
  const [topic, setTopic] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();
    const newTopic = { topic };
    try {
      const response = await axios.post(
        "http://localhost:8085/api/topics",
        newTopic
      );
      console.log("Topic created:", response.data);
      toast.success("Topic created successfully!");
    } catch (error) {
      console.error("Error creating topic:", error);
      toast.warn("An error occurred. Please try again!");
    }
  };

  return (
    <div>
      <Header />
      <form className="category-form" onSubmit={handleSubmit}>
        <div>
          <label className="category-label"><strong>Topic:</strong></label>
          <input
            className="category-input"
            type="Category-Topic"
            value={topic}
            onChange={(e) => setTopic(e.target.value)}
            placeholder="Enter the topic of the day"
          />
        </div>
        <button className="category-button" type="submit">
          Submit
        </button>
        <ToastContainer />
      </form>
    </div>
  );
};

export default CategoryAdmin;
