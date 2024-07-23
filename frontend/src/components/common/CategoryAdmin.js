import React, { useState } from 'react';
import axios from 'axios';

const AdminPage = () => {
  const [topic, setTopic] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    const newTopic = { topic };
    try {
      const response = await axios.post('http://localhost:8085/api/topics', newTopic);
      console.log('Topic created:', response.data);
    } catch (error) {
      console.error('Error creating topic:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Topic:</label>
        <input
          type="text"
          value={topic}
          onChange={(e) => setTopic(e.target.value)}
        />
      </div>
      <button type="submit">Submit</button>
    </form>
  );
};

export default AdminPage;
