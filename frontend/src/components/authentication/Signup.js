import React, { useState } from "react";
import "./Login.css"; // Using the same CSS file for simplicity
import { Link, useLocation } from "react-router-dom";
import { validateEmail, validatePassword } from "./SignupValidation";
import axios from "axios";

const Signup = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({});
  const location = useLocation();

  const handleSubmit = async (event) => {
    event.preventDefault();
    let validationErrors = {};

    if (!name) {
      validationErrors.name = "Name is required.";
    }

    if (!email || !validateEmail(email)) {
      validationErrors.email =
        "Invalid email. Only @dal.ca addresses are accepted.";
    }

    const passwordErrors = validatePassword(password);
    if (passwordErrors.length > 0) {
      validationErrors.password = passwordErrors;
    }

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
    } else {
      // Perform signup action with name, email, and password
      console.log("Name:", name);
      console.log("Email:", email);
      console.log("Password:", password);
      setErrors({});
    }

    const user = {
      name,
      email,
      password,
    };

    try {
      // Submit form data to the backend
      const response = await axios.post(
        "http://localhost:8085/user/create",
        user
      );
      console.log(response.data);
      alert("User created successfully");
    } catch (error) {
      console.error("Error signing up:",error);
      alert("An error occurred. Please try again!");
    }
  };

  return (
    <div className="d-flex vh-100 justify-content-center align-items-center bg-primary">
      <div className="p-3 bg-white w-25">
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="name">Name</label>
            <input
              type="text"
              placeholder="Enter Name"
              className="form-control"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
            {errors.name && <p className="text-danger">{errors.name}</p>}
          </div>

          <div>
            <label htmlFor="email">Email</label>
            <input
              type="email"
              placeholder="Enter Email"
              className="form-control"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            {errors.email && <p className="text-danger">{errors.email}</p>}
          </div>

          <div>
            <label htmlFor="password">Password</label>
            <input
              type="password"
              placeholder="Enter Password"
              className="form-control"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            {errors.password && (
              <ul className="text-danger">
                {errors.password.map((error, index) => (
                  <li key={index}>{error}</li>
                ))}
              </ul>
            )}
          </div>

          <div className="btn-group">
            <Link
              to="/login"
              className={`btn ${
                location.pathname === "/login" ? "btn-success" : "btn-default"
              } text-decoration-none`}
            >
              Login
            </Link>
            <button
              className={`btn ${
                location.pathname === "/signup" ? "btn-success" : "btn-default"
              }`}
              type="submit"
            >
              Signup
            </button>
          </div>

          <p className="mt-3">You agree to our terms and policies</p>
        </form>
      </div>
    </div>
  );
};

export default Signup;
