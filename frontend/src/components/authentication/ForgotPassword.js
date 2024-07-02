import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './ForgotPassword.css';

export default function SecurityQuestion() {
  const [email, setEmail] = useState('');
  const [securityAnswer, setSecurityAnswer] = useState('');
  const [backendBirthday, setBackendBirthday] = useState('');
  const navigate = useNavigate();

  const handleEmailSubmit = async (e) => {
    e.preventDefault();
    console.log("Submitting email...", email);

    try {
      const response = await fetch(`http://localhost:8081/user/birthday/${email}`);
      if (response.ok) {
        const data = await response.json();
        setBackendBirthday(data.birthday); // Assuming the backend returns birthday in data
        console.log("Birthday retrieved from backend:", data.birthday);
      } else {
        console.error("Failed to fetch birthday from backend");
        // Handle error if needed
      }
    } catch (error) {
      console.error("Error fetching birthday from backend:", error);
      // Handle error if needed
    }
  };

  const handleSecurityQuestion = (e) => {
    e.preventDefault();
    console.log("Answering security question...");
    if (backendBirthday === securityAnswer) {
      console.log("Security answer is correct");
      navigate("/ResetPassword");
    } else {
      console.log("Security answer is incorrect");
      alert("Incorrect answer, please try again.");
    }
  };

  return (
    <div className="container">
      <div className="paper">
        <h2 className="subtitle">Enter Email</h2>
        <form className="form" onSubmit={handleEmailSubmit} noValidate autoComplete="off">
          <input
            type="email"
            placeholder="Enter Email"
            className="input"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <button type="submit" className="button">Submit</button>
        </form>
      </div>

      {backendBirthday && (
        <div className="paper">
          <h2 className="subtitle">Security Question: What is your Birthday?</h2>
          <form className="form" onSubmit={handleSecurityQuestion} noValidate autoComplete="off">
            <input
              type="text"
              placeholder="Answer Security Question (Birthday)"
              className="input"
              value={securityAnswer}
              onChange={(e) => setSecurityAnswer(e.target.value)}
            />
            <button type="submit" className="button">Submit</button>
          </form>
        </div>
      )}
    </div>
  );
}
