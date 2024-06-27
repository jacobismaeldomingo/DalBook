import React, { useState } from "react";
import "./Login.css";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { validateEmail, validatePassword } from "./SignupValidation";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({});
  const location = useLocation();
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    let validationErrors = {};

    // Perform login action with email and password
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
      return;
    }

    try {
      const response = await fetch("http://localhost:8085/api/user/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        // Login successful
        const userID = await response.json(); // Parse response body as JSON
        console.log("Login successful");
        // Redirect or set state to indicate logged in
        navigate("/profile");
      } else {
        // Login failed
        const errorText = await response.text();
        console.log("Login failed");
        // Handle error state or display error message
        alert(errorText);
      }
    } catch (error) {
      console.error("Error logging in:", error);
      // Handle network error
    }
  };

  return (
    <div className="d-flex vh-100 justify-content-center align-items-center bg-primary login-page">
      <div className="p-3 bg-white w-25">
        <form onSubmit={handleSubmit} className="signup-form">
          <div>
            <label htmlFor="email" className="label-name">Email</label>
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
            <label htmlFor="password" className="label-name">Password</label>
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
            {/* <Link
              to="/home"
              className={`btn ${
                location.pathname === "./home" ? "btn-success" : "btn-default"
              }`}
              type="submit"
            >
              Login
            </Link> */}
            <button
              type="submit"
              className="btn btn-default"
            >
              Login
            </button>
            <Link
              to="/signup"
              className={`btn ${
                location.pathname === "/signup" ? "btn-success" : "btn-default"
              } text-decoration-none`}
            >
              SignUp
            </Link>
          </div>

          <div className="forget-password">
            <Link to="/ForgotPassword">Forget password?</Link>
          </div>
          <p className="mt-3">You agree to our terms and policies</p>
        </form>
      </div>
    </div>
  );
}

export default Login;
