import axios from "axios";

const API_URL = "http://localhost:8085/api/friends";

const sendFriendRequest = (senderId, receiverId) => {
  return axios.post(`${API_URL}/send`, null, {
    params: { senderId, receiverId },
  });
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
