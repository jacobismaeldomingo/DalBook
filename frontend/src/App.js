import React from 'react';
import './App.css';
import Login from './components/Login';
import Signup from './components/Signup';
import Header from './components/Header';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/home" element={<Header />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
