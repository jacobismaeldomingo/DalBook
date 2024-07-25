import { React, useEffect } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import "./css/App.css";
import ForgotPassword from "./components/password/ForgotPassword";
import ResetPassword from "./components/password/ResetPassword";
import Login from "./components/authentication/Login";
import Signup from "./components/authentication/Signup";
import Home from "./components/common/Home";
import Error from "./components/common/Error";
import EditProfile from "./components/feed/EditProfile";
import Profile from "./components/feed/Profile";
import FriendRequest from "./components/friendRequests/FriendRequest";
import FriendRequestList from "./components/friendRequests/FriendRequestList";
import FriendsList from "./components/friendRequests/FriendsList";
import PrivateRoute from "./components/common/PrivateRoute";

function App() {
  useEffect(() => {
    document.title = "DalBook";
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        {/* Common paths user can access before login/signup */}
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/forgotPassword" element={<ForgotPassword />} />
        <Route path="/resetPassword" element={<ResetPassword />} />

        {/* Restricted paths that user can access after login/signup */}
        <Route
          path="/home"
          element={
            <PrivateRoute>
              <Home />
            </PrivateRoute>
          }
        />
        <Route
          path="/profile"
          element={
            <PrivateRoute>
              <Profile />
            </PrivateRoute>
          }
        />
        <Route
          path="/editProfile"
          element={
            <PrivateRoute>
              <EditProfile />
            </PrivateRoute>
          }
        />
        <Route
          path="/friendRequest"
          element={
            <PrivateRoute>
              <FriendRequest />
            </PrivateRoute>
          }
        />
        <Route
          path="/friendRequestList"
          element={
            <PrivateRoute>
              <FriendRequestList />
            </PrivateRoute>
          }
        />
        <Route
          path="/friendsList"
          element={
            <PrivateRoute>
              <FriendsList />
            </PrivateRoute>
          }
        />
        <Route path="*" element={<Error />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
