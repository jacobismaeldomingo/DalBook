import React, { useState, useEffect } from "react";
import "./Login.css";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { validateEmail } from "./SignupValidation";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({});
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoggedIn, setIsLoggedIn] = useState(false); // Track if user is already logged in
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    // Check if the user is already logged in
    const loggedIn = localStorage.getItem("isLoggedIn");
    if (loggedIn === "true") {
      setIsLoggedIn(true);
      navigate("/home"); // Redirect to home page if already logged in
    }
  }, [navigate]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    let validationErrors = {};
    setErrorMessage(""); // Reset error message

    // Perform login action with email and password
    if (!email || !validateEmail(email)) {
      validationErrors.email =
        "Please enter your email. Only @dal.ca addresses are accepted.";
    }

    if (!password) {
      validationErrors.password = "Please enter your password.";
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
        const userId = await response.json(); // Retreive the User Id
        console.log("Login successful");

        // Store login flag in local storage
        localStorage.setItem("isLoggedIn", "true");
        localStorage.setItem("userId", userId);
        localStorage.setItem("userEmail", email);

        // Redirect to the user home page
        navigate("/home");
      } else {
        // Login failed
        const errorText = await response.text();
        setErrorMessage("Wrong password or email");
        console.log("Login failed:", errorText);
        setErrors(""); // Reset the error messages
      }
    } catch (error) {
      console.error("Error logging in:", error);
      // Handle network error
      setErrorMessage("Network error occurred. Please try again later.");
    }
  };

  // if (isLoggedIn) {
  //   return (
  //     <div className="d-flex vh-100 justify-content-center align-items-center bg-primary login-page">
  //       <div className="p-3 bg-white w-25">
  //         <p>You are already logged in. Please log out first to switch accounts.</p>
  //         <button
  //           onClick={() => {
  //             localStorage.clear();
  //             setIsLoggedIn(false);
  //           }}
  //           className="btn btn-danger"
  //         >
  //           Log Out
  //         </button>
  //       </div>
  //     </div>
  //   );
  // }

  return (
    <div className="d-flex vh-100 justify-content-center align-items-center bg-primary login-page">
      <div className="p-3 bg-white w-25">
        <form onSubmit={handleSubmit} className="signup-form">
          <div>
            <label htmlFor="email" className="label-name">
              Email
            </label>
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
            <label htmlFor="password" className="label-name">
              Password
            </label>
            <input
              type="password"
              placeholder="Enter Password"
              className="form-control"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            {errors.password && (
              <p className="text-danger">{errors.password}</p>
            )}
          </div>

          {errorMessage && <div className="error-message">{errorMessage}</div>}

          <div className="btn-group">
            <button type="submit" className="btn btn-success">
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
            <Link to="/forgotPassword">Forget password?</Link>
          </div>
          <p className="mt-3">You agree to our terms and policies</p>
        </form>
      </div>
    </div>
  );
}

export default Login;
