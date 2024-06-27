import React, { useState, useEffect } from 'react';
import axios from 'axios';

const FriendList = () => {
  const [friends, setFriends] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Fetch the friend list from the backend
    axios.get('/api/friends')
      .then(response => {
        setFriends(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error('There was an error fetching the friends!', error);
      });
  }, []);

  const handleRemoveFriend = (friendId) => {
    axios.post('/api/friends/remove', { id: friendId })
      .then(response => {
        setFriends(friends.filter(friend => friend.id !== friendId));
      })
      .catch(error => {
        console.error('There was an error removing the friend!', error);
      });
  };

  const handleBlockFriend = (friendId) => {
    axios.post('/api/friends/block', { id: friendId })
      .then(response => {
        setFriends(friends.filter(friend => friend.id !== friendId));
      })
      .catch(error => {
        console.error('There was an error blocking the friend!', error);
      });
  };

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <div>
      <h1>My Friends</h1>
      <ul>
        {friends.map(friend => (
          <li key={friend.id}>
            {friend.name}
            <button onClick={() => handleRemoveFriend(friend.id)}>Remove</button>
            <button onClick={() => handleBlockFriend(friend.id)}>Block</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default FriendList;
