import React, { useState } from 'react';
import './Login.css';
import { Link, useLocation } from 'react-router-dom';
import { validateEmail, validatePassword } from "./SignupValidation";


function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const location = useLocation();
  let validationErrors = {};


  const handleSubmit = async(event) => {
    event.preventDefault();
     // Perform login action with email and password
    // console.log('Email:', email);
    // console.log('Password:', password);

      if (!email || !validateEmail(email)) {
        validationErrors.email =
          "Invalid email. Only @dal.ca addresses are accepted.";
      }
  
      const passwordErrors = validatePassword(password);
      if (passwordErrors.length > 0) {
        validationErrors.password = passwordErrors;
      }

      try {
        const response = await fetch('http://localhost:8085/user/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });


      if (response.ok) {
        // Login successful
        const userID = await response.json(); // Parse response body as JSON
        console.log('Login successful');
        // Redirect or set state to indicate logged in
      } else {
        // Login failed
        const errorText = await response.text();
        console.log('Login failed');
        // Handle error state or display error message
      }
    } catch (error) {
      console.error('Error logging in:', error);
      // Handle network error
    }

  };

  return (
    <div className='d-flex vh-100 justify-content-center align-items-center bg-primary'>
      <div className='p-3 bg-white w-25'>
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="email">Email</label>
            <input
              type="email"
              placeholder='Enter Email'
              className='form-control'
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div>
            <label htmlFor="password">Password</label>
            <input
              type="password"
              placeholder='Enter Password'
              className='form-control'
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          <div className='btn-group'>
            <button className={`btn ${location.pathname === '/login' ? 'btn-success' : 'btn-default'}`} type="submit">Login</button>
            <Link to='/signup' className={`btn ${location.pathname === '/signup' ? 'btn-success' : 'btn-default'} text-decoration-none`}>SignUp</Link>
          </div>

        </form>
      </div>
    </div>
  );
}

export default Login;
