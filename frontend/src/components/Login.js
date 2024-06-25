import React, { useState } from 'react';
import './Login.css';
import { Link, useLocation } from 'react-router-dom';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const location = useLocation();

  const handleSubmit = (event) => {
    event.preventDefault();
    // Perform login action with email and password
    console.log('Email:', email);
    console.log('Password:', password);
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

          <p className="mt-3">You agree to our terms and policies</p>
        </form>
      </div>
    </div>
  );
}

export default Login;
