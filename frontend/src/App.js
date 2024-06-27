import React from "react";
import "./App.css";
import ForgotPassword from "./components/password/ForgotPassword";
import ResetPassword from "./components/password/ResetPassword";
import Login from "./components/authentication/Login";
import Signup from "./components/authentication/Signup";
import Header from "./components/common/Header";
import Error from "./components/common/Error";
import FriendRequestSender from "./components/friendRequests/FriendRequest";
import FriendRequestList from "./components/friendRequests/FriendRequestList";
import FriendsList from "./components/friendRequests/FriendsList";
import "./App.css";
import ForgotPassword from "./ForgotPassword";
import ResetPassword from "./ResetPassword";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/home" element={<Header />} />
        <Route path="/forgotPassword" element={<ForgotPassword />} />
        <Route path="/resetPassword" element={<ResetPassword />} />
        <Route
          path="/friendRequest"
          element={<FriendRequestSender userId={"25"} />}
        />
        <Route
          path="/friendRequestList"
          element={<FriendRequestList userId={"26"} />}
        />
        <Route path="/friendsList" element={<FriendsList userId={"26"} />} />
        <Route path="*" element={<Error />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
