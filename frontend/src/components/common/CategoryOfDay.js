import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './CategoryOfDay.css'; 

const PostForm = () => {
  const [content, setContent] = useState('');
  const [topic, setTopic] = useState('');

  useEffect(() => {
    const fetchTopic = async () => {
      try {
        const response = await axios.get('http://localhost:8085/api/topics/latest');
        setTopic(response.data.topic);
      } catch (error) {
        console.error('Error fetching topic:', error);
      }
    };

    fetchTopic();
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const userId = localStorage.getItem('userId');
    const post = { userId, topic, content };
    
    try {
      const response = await axios.post(
        'http://localhost:8085/Category/posts',
        post
      );
      console.log('Post created:', response.data);
    } catch (error) {
      console.error('Error creating post:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Topic:</label>
        <h2>Today's category is about '{topic}'. Tell us what you think:</h2>
      </div>
      <div>
        <label>Post Content:</label>
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
        ></textarea>
      </div>
      <button type="submit">Submit</button>
    </form>
  );
};

export default PostForm;
