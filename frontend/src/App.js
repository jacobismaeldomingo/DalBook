import React from "react";
import "./App.css";
import ForgotPassword from "./components/password/ForgotPassword";
import ResetPassword from "./components/password/ResetPassword";
import Login from "./components/authentication/Login";
import Signup from "./components/authentication/Signup";
import Header from "./components/common/Header";
import Error from "./components/common/Error";
import UserProfile from "./components/feed/UserProfile";
import FriendRequest from "./components/friendRequests/FriendRequest";
import FriendRequestList from "./components/friendRequests/FriendRequestList";
import FriendsList from "./components/friendRequests/FriendsList";
import PrivateRoute from "./components/common/PrivateRoute";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route
          path="/home"
          element={
            <PrivateRoute>
              <Header />
            </PrivateRoute>
          }
        />
        <Route path="/forgotPassword" element={<ForgotPassword />} />
        <Route path="/resetPassword" element={<ResetPassword />} />
        <Route
          path="/profile"
          element={
            <PrivateRoute>
              <UserProfile />
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
