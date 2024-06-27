import React, { useState, useEffect } from 'react';
import './UserProfile.css';
import axios from 'axios';

const UserProfile = () => {
  const [user, setUser] = useState({
    fullName: '',
    email: '',
    bio: '',
    profilePicture: null,
    currentPassword: '',
    status: 'Available',
  });
  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState('');
  const [previewProfilePicture, setPreviewProfilePicture] = useState(null);

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get('http://localhost:8085/api/user/profile', {
          headers: { Authorization: `Bearer ${token}` },
        });
        setUser(response.data);
        setPreviewProfilePicture(response.data.profilePicture);
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };

    fetchUserData();
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();

    let validationErrors = {};
    if (!user.bio) {
      validationErrors.bio = "Bio is required.";
    }

    if (!user.profilePicture) {
      validationErrors.profilePicture = "Profile picture is required.";
    }

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
    } else {
      const formData = new FormData();
      formData.append('fullName', user.fullName);
      formData.append('email', user.email);
      formData.append('bio', user.bio);
      formData.append('status', user.status);
      if (user.profilePicture) {
        formData.append('profilePicture', user.profilePicture);
      }

      try {
        const token = localStorage.getItem('token');
        const response = await axios.post('http://localhost:8085/api/user/profile/update', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
            Authorization: `Bearer ${token}`,
          },
        });
        console.log(response.data);
        setMessage('Profile updated successfully');
        setErrors({});
      } catch (error) {
        console.error(error);
        setMessage('An error occurred. Please try again!');
      }
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const handleFileChange = (e) => {
    const { name, files } = e.target;
    setUser({ ...user, [name]: files[0] });
    if (name === 'profilePicture') {
      setPreviewProfilePicture(URL.createObjectURL(files[0]));
    }
  };

  return (
    <div className="d-flex vh-100 justify-content-center align-items-center bg-light">
      <div className="p-3 bg-white w-50">
        <h1>Edit Profile</h1>
        {message && <p className="text-info">{message}</p>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="fullName">Full Name:</label>
            <input
              type="text"
              id="fullName"
              name="fullName"
              placeholder="Enter your full name"
              className="form-control"
              value={user.fullName}
              onChange={handleInputChange}
            />
          </div>
          <div className="form-group">
            <label htmlFor="email">Email:</label>
            <input
              type="email"
              id="email"
              name="email"
              placeholder="Enter your email"
              className="form-control"
              value={user.email}
              onChange={handleInputChange}
            />
          </div>
          <div className="form-group">
            <label htmlFor="bio">Bio:</label>
            <textarea
              id="bio"
              name="bio"
              placeholder="Enter your bio"
              className="form-control"
              value={user.bio}
              onChange={handleInputChange}
            ></textarea>
            {errors.bio && <p className="text-danger">{errors.bio}</p>}
          </div>
          <div className="form-group">
            <label htmlFor="profilePicture">Profile Picture:</label>
            <input
              type="file"
              id="profilePicture"
              name="profilePicture"
              className="form-control"
              onChange={handleFileChange}
            />
            {previewProfilePicture && (
              <img
                src={previewProfilePicture}
                alt="Profile Preview"
                className="img-thumbnail mt-2"
                style={{ width: '150px', height: '150px' }}
              />
            )}
            {errors.profilePicture && <p className="text-danger">{errors.profilePicture}</p>}
          </div>
          <div className="form-group">
            <label htmlFor="status">Status:</label>
            <select
              id="status"
              name="status"
              className="form-control"
              value={user.status}
              onChange={handleInputChange}
            >
              <option value="Available">Available</option>
              <option value="Busy">Busy</option>
              <option value="Do not disturb">Do not disturb</option>
              <option value="Offline">Offline</option>
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="currentPassword">Current Password:</label>
            <input
              type="password"
              id="currentPassword"
              name="currentPassword"
              placeholder="Enter your current password"
              className="form-control"
              value={user.currentPassword}
              onChange={handleInputChange}
            />
          </div>
          <button type="button" className="btn btn-secondary">Change Password</button>
          <button type="submit" className="btn btn-primary ml-2">Save Changes</button>
        </form>
      </div>
    </div>
  );
};

export default UserProfile;
