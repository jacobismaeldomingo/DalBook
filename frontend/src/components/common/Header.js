// Home Page
import React from "react";
import "./Header.css";
import { useNavigate } from "react-router-dom";
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

function Header() {
  const navigate = useNavigate();

  const homePage = () => {
    navigate("/home");
  };

  const handleLogout = () => {
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("userId");
    localStorage.removeItem("userEmail");
    navigate("/login");
  };

  const handleFriendRequests = () => {
    navigate("/FriendRequest");
  };

  const handleFriendRequestsList = () => {
    navigate("/FriendRequestList");
  };

  const friendsPage = () => {
    navigate("/friendsList");
  };

  return (
    <div className="homepage">
      <div className="header">
        <div className="search-header">
          <div className="logo">
            <img
              src="/images/dalbook_logo.png"
              alt="logo"
              style={{
                height: "40px",
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
          {/* <div className="icon">
            <IconBuildingStore stroke={2} size={30} color="#1877F2" />
          </div> */}
          <div className="icon" onClick={friendsPage}>
            <IconUsers stroke={2} size={30} color="#1877F2" />
          </div>
          <div className="icon" onClick={handleFriendRequests}>
            <IconUsersGroup stroke={2} size={30} color="#1877F2" />
          </div>
        </div>
        <div className="account-header">
          <div className="menu">
            <IconMenu2 stroke={2} size={30} color="#1877F2" />
          </div>
          <div className="messages">
            <IconMessages stroke={2} size={30} color="#1877F2" />
          </div>
          <div className="notifications" onClick={handleFriendRequestsList}>
            <IconBell stroke={2} size={30} color="#1877F2" />
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
  );
}

export default Header;
