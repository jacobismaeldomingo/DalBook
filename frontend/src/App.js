import React from "react";
import "./App.css";
import ForgotPassword from "./ForgotPassword";
import ResetPassword from "./ResetPassword";
import Login from "./components/authentication/Login";
import Signup from "./components/authentication/Signup";
import Header from "./components/common/Header";
import Error from "./components/common/Error";
import FriendRequestSender from "./components/friendRequests/FriendRequest";
import FriendRequestList from "./components/friendRequests/FriendRequestList";
import FriendsList from "./components/friendRequests/FriendsList";
import './App.css';
import ForgotPassword from "./ForgotPassword";
import ResetPassword from "./ResetPassword";
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/home" element={<Header />} />
        <Route
          path="/friendrequest"
          element={<FriendRequestSender userId={"25"} />}
        />
        <Route
          path="/friendrequestlist"
          element={<FriendRequestList userId={"26"} />}
        />
        <Route path="/friendslist" element={<FriendsList userId={"26"} />} />
        <Route path="*" element={<Error />} />
        <Route path="/ForgotPassword" element={<ForgotPassword />} />
        <Route path="/ResetPassword" element={<ResetPassword />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
