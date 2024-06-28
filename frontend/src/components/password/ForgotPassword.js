import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Forgot.css";
import axios from "axios";

export default function SecurityQuestion() {
  const [email, setEmail] = useState("");
  const [securityAnswer, setSecurityAnswer] = useState("");
  const [securityQuestion, setSecurityQuestion] = useState("");
  // const [backendBirthday, setBackendBirthday] = useState('');
  const navigate = useNavigate();

  const handleEmailSubmit = async (e) => {
    e.preventDefault();
    // console.log("Submitting email...", email);

    try {
      // const response = await axios.get(
      //   `http://localhost:8085/api/user/get/${email}`
      // );
      const response = await fetch(
        `http://localhost:8085/api/user/get/${email}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      if (response.ok) {
        const data = await response.json();
        setSecurityQuestion(data.securityAnswer);
        console.log("User information successfull:");
      } else {
        console.error("Failed to fetch user from backend");
        alert("Failed to fetch user from backend");
      }
    } catch (error) {
      console.error("Error fetching user from backend:", error);
      alert("Error fetching user from backend");
    }
  };

  const handleSecurityQuestion = (e) => {
    e.preventDefault();
    console.log("Answering security question...");
    if (securityQuestion === securityAnswer) {
      console.log("Security answer is correct");
      navigate("/resetPassword"); // Redirect to Change Password Page
    } else {
      console.log("Security answer is incorrect");
      alert("Incorrect answer, please try again.");
    }
  };

  return (
    <div className="container">
      <div className="paper">
        <h2 className="subtitle">Enter Email</h2>
        <form
          className="form"
          onSubmit={handleEmailSubmit}
          noValidate
          autoComplete="off"
        >
          <input
            type="email"
            placeholder="Enter Email"
            className="input"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <button type="submit" className="button">
            Submit
          </button>
        </form>
      </div>

      {securityQuestion && (
        <div className="paper">
          <h2 className="subtitle">
            Security Question: What is your favorite destination to visit?
          </h2>
          <form
            className="form"
            onSubmit={handleSecurityQuestion}
            noValidate
            autoComplete="off"
          >
            <input
              type="text"
              placeholder="Answer to the Security Question"
              className="input"
              value={securityAnswer}
              onChange={(e) => setSecurityAnswer(e.target.value)}
            />
            <button type="submit" className="button">
              Submit
            </button>
          </form>
        </div>
      )}
    </div>
  );
}
