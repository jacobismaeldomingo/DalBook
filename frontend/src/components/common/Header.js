// Home Page
import React from "react";
import "./Header.css";
import { Link, useLocation } from "react-router-dom";
import {
  IconSearch,
  IconHome,
  IconUsersGroup,
  IconBuildingStore,
  IconMenu2,
  IconMessages,
  IconBell,
  IconUserCircle,
} from "@tabler/icons-react";
import Feed from "../feed/Feed";

function Header() {
  return (
    <div className="homepage">
      <div className="header">
        <div className="search-header">
          <div className="logo">
            <img
              src="/images/logo.png"
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
          <div className="icon">
            {/* <IconHomeFilled size={30} color="#1877F2"/> */}
            <IconHome stroke={2} size={30} color="#1877F2" />
          </div>
          <div className="icon">
            <IconBuildingStore stroke={2} size={30} color="#1877F2" />
          </div>
          <div className="icon">
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
          <div className="notifications">
            <IconBell stroke={2} size={30} color="#1877F2" />
          </div>
          <div className="profile">
            <IconUserCircle stroke={2} size={30} color="#1877F2" />
          </div>
        </div>
      </div>
      <Feed />
    </div>
  );
}

export default Header;
