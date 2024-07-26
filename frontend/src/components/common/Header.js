// Home Page

import { React, useState, useEffect } from "react";
import "./Header.css";
import { useNavigate } from "react-router-dom";
import friendService from "../../services/FriendService";
import {
  IconSearch,
  IconHome,
  IconUsersGroup,
  IconMenu2,
  IconMessages,
  IconBell,
  IconLogout,
  IconUsers,
} from "@tabler/icons-react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function Header() {
  const navigate = useNavigate();
  const [isAdmin, setIsAdmin] = useState(false);
  const [pendingRequests, setPendingRequests] = useState(0);

  useEffect(() => {
    const userRole = localStorage.getItem("userRole");
    setIsAdmin(userRole === "System_Admin");
  }, []);

  useEffect(() => {
    const storedUserId = localStorage.getItem("userId");
    if (storedUserId) {
      friendService
        .getPendingRequests(storedUserId)
        .then((response) => {
          setPendingRequests(response.data.length);
        })
        .catch((error) => {
          console.error("Error fetching pending requests:", error);
          toast.warn("An error occurred. Please try again!");
        });
    }
  }, []);

  const homePage = () => {
    navigate("/home");
  };

  const handleLogout = () => {
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("userId");
    localStorage.removeItem("userEmail");
    localStorage.removeItem("userRole");
    navigate("/login");
  };

  // const handleFriendRequests = () => {
  //   navigate("/FriendRequest");
  // };

  const handleFriendRequestsList = () => {
    navigate("/FriendRequestList");
  };

  const friendsPage = () => {
    navigate("/friends");
  };

  const adminPage = () => {
    navigate("/admin");
  };

  const groupsPage = () => {
    navigate("/groupDashboard");
  };

  return (
    <>
      <ToastContainer />
      <div className="homepage">
        <div className="header">
          <div className="main-header">
            <div className="logo">
              <img
                src="/images/dalbook_logo.png"
                alt="logo"
                style={{
                  height: "40px",
                  cursor: "auto",
                }}
              />
            </div>
            <div className="search-bar">
              <IconSearch stroke={2} />
              <input placeholder="Search Facebook" type="Search" />
            </div>
          </div>
          <div className="navigation-header">
            <div
              className="icon"
              onClick={homePage}
              style={{ cursor: "pointer" }}
            >
              <IconHome stroke={2} size={30} color="#1877F2" />
            </div>
            <div className="icon" onClick={friendsPage}>
              <IconUsers stroke={2} size={30} color="#1877F2" />
            </div>
            <div className="icon" onClick={groupsPage}>
              <IconUsersGroup stroke={2} size={30} color="#1877f2" />
            </div>
          </div>
          <div className="account-header">
            {isAdmin && (
              <div className="menu" onClick={adminPage}>
                <IconMenu2 stroke={2} size={30} color="#1877F2" />
              </div>
            )}
            <div className="messages">
              <IconMessages stroke={2} size={30} color="#1877F2" />
            </div>
            <div
              className="notifications"
              onClick={handleFriendRequestsList}
              style={{ position: "relative" }}
            >
              <IconBell stroke={2} size={30} color="#1877f2" />
              {pendingRequests > 0 && (
                <span
                  style={{
                    position: "absolute",
                    top: 0,
                    right: 0,
                    width: "10px",
                    height: "10px",
                    backgroundColor: "red",
                    borderRadius: "50%",
                  }}
                ></span>
              )}
            </div>
            <div
              className="profile-header"
              onClick={handleLogout}
              style={{ cursor: "pointer" }}
            >
              <IconLogout stroke={2} size={30} color="#1877F2" />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Header;
