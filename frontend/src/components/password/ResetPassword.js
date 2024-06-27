import React, { useState } from 'react';
import './Forgot.css'

export default function Reset() {
  const [newPassword, setNewPassword] = useState('');

  const handleChangePassword = (e) => {
    e.preventDefault();
    console.log("Changing password...");

    const updatedUser = {
      password: newPassword,
      id: 1 // Assuming a static ID for demonstration purposes, replace with dynamic data
    };

    fetch(`http://localhost:8081/user/updatePassword/${updatedUser.id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(updatedUser)
    }).then(() => {
      console.log("Password updated successfully");
      alert("Password updated successfully");
    }).catch((error) => {
      console.error("Error updating password:", error);
      alert("Failed to update password");
    });
  };

  return (
    <div className="container">
    <div className="paper">
      <h2 className="subtitle">Change Password</h2>
      <form className="form" onSubmit={handleChangePassword} noValidate autoComplete="off">
        <input
          type="password"
          placeholder="New Password"
          className="input"
          value={newPassword}
          onChange={(e) => setNewPassword(e.target.value)}
        />
        <button type="submit" className="button">Change Password</button>
      </form>
    </div>
    </div>
  );
}
