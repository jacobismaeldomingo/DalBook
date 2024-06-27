import React from 'react';
import './App.css';
//import Login from './components/Login';
//import Signup from './SignUp';
import ForgotPassword from "./components/authentication/ForgotPassword";
import ResetPassword from "./components/authentication/ResetPassword";
//import Header from './components/Header';
//import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Login from "./components/authentication/Login";
import Signup from "./components/authentication/Signup";
import Header from "./components/common/Header";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/common/Header" element={<Header />} />
        <Route path="/ForgotPassword" element={<ForgotPassword />} />
        <Route path="/ResetPassword" element={<ResetPassword />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
