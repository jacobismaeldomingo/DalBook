import React, { useState } from 'react';
import './App.css'; 
import './Forgot.css'

export default function ResetPassword() {
  const [email, setEmail] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [error, setError] = useState(null);

  const handleChangePassword = async (e) => {
    e.preventDefault();
    console.log("Changing password...");

    const updatedUser = {
      email: email,
      newPassword: newPassword
    };

    try {
      const response = await fetch(`http://localhost:8085/user/reset-password`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(updatedUser)
      });

      if (!response.ok) {
        throw new Error('Failed to update password');
      }

      console.log("Password updated successfully");
      alert("Password updated successfully");
      setEmail('');
      setNewPassword('');
      setError(null);
    } catch (error) {
      console.error("Error updating password:", error);
      setError('Failed to update password');
    }
  };

  return (
    <div className="container">
      <div className="paper">
        <h2 className="subtitle">Reset Password</h2>
        <form className="form" onSubmit={handleChangePassword} noValidate autoComplete="off">
          <input
            type="email"
            placeholder="Email"
            className="input"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <input
            type="password"
            placeholder="New Password"
            className="input"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
          />
          <button type="submit" className="button">Reset Password</button>
          {error && <p className="error">{error}</p>}
        </form>
      </div>
    </div>
  );
}
