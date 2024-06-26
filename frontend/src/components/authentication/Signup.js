import React, { useState } from "react";
import "./Login.css"; // Using the same CSS file for simplicity
import { Link, useLocation } from "react-router-dom";
import { validateEmail, validatePassword } from "./SignupValidation";
import axios from "axios";

const Signup = () => {
  const [firstName, setfName] = useState("");
  const [lastName, setlName] = useState("");
  const [dateOfBirth, setBirthday] = useState("");
  const [bio, setBio] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({});
  const location = useLocation();

  const handleSubmit = async (event) => {
    event.preventDefault();
    let validationErrors = {};

    if (!firstName) {
      validationErrors.name = "First Name is required.";
    }

    if (!dateOfBirth) {
      validationErrors.birthday = "Date of Birth is required.";
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
      console.log("First Name:", firstName);
      console.log("Last Name:", lastName);
      console.log("Birthday:", dateOfBirth);
      console.log("Bio:", bio);
      console.log("Email:", email);
      console.log("Password:", password);
      setErrors({});
    }

    const user = {
      firstName,
      lastName,
      dateOfBirth,
      bio,
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
            <label htmlFor="name">First Name</label>
            <input
              type="text"
              className="form-control"
              value={firstName}
              onChange={(e) => setfName(e.target.value)}
            />
            {errors.name && <p className="text-danger">{errors.name}</p>}
          </div>
          <div>
            <label htmlFor="name">Last Name</label>
            <input
              type="text"
              className="form-control"
              value={lastName}
              onChange={(e) => setlName(e.target.value)}
            />
            {errors.name && <p className="text-danger">{errors.name}</p>}
          </div>

          <div>
            <label htmlFor="birthday">Birthday</label>
            <input
              type="date"
              className="form-control"
              value={dateOfBirth}
              onChange={(e) => setBirthday(e.target.value)}
            />
            {errors.birthday && <p className="text-danger">{errors.birthday}</p>}
          </div>

          <div>
            <label htmlFor="bio">Bio</label>
            <textarea
              placeholder="Write about yourself"
              className="form-control"
              value={bio}
              onChange={(e) => setBio(e.target.value)}
            />
            {errors.bio && <p className="text-danger">{errors.bio}</p>}
          </div>

          <div>
            <label htmlFor="email">Email</label>
            <input
              type="email"
              placeholder="@dal.ca"
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
