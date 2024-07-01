import axios from "axios";

const API_URL = "http://localhost:8085/api/friends";

const sendFriendRequest = async (senderId, receiverEmail) => {
  try {
    const response = await axios.post(`${API_URL}/send`, null, {
      params: { senderId, receiverEmail },
    });
    console.log('Friend request sent successfully:', response.data);
    return response.data;

  } catch (error) {
    if (error.response) {
      const { timestamp, message, details } = error.response.data;
      console.error('Error details:', timestamp, message, details);

      // Handle different error scenarios based on `message`
      if (message === "Friend request already sent") {
        alert("Friend request already sent");
      }
      else if (message === "Already a friend") {
        alert("Already a friend");
      } 
      else {
        console.log(error.response.data);
        alert('Failed to send friend request');
      }
    } 
  }
};

const acceptFriendRequest = (requestId) => {
  return axios.post(`${API_URL}/accept`, null, { params: { requestId } });
};

const getPendingRequests = (userId) => {
  return axios.get(`${API_URL}/pending`, { params: { userId } });
};

const getFriends = (userId) => {
  return axios.get(`${API_URL}/list`, { params: { userId } });
};

export default {
  sendFriendRequest,
  acceptFriendRequest,
  getPendingRequests,
  getFriends,
};
