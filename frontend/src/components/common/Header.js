// Home Page

import { React, useState, useEffect } from "react";
import "./Header.css";
import { useNavigate } from "react-router-dom";
import friendService from "../../services/FriendService";
import axios from "axios";
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
  const [joinRequestsApproved, setJoinRequestsApproved] = useState(0);
  const [newCategoryTopic, setNewCategoryTopic] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [allUsers, setAllUsers] = useState([]);
  const [allGroups, setAllGroups] = useState([]);
  const [searchFilter, setSearchFilter] = useState("all"); // Filter state
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  useEffect(() => {
    const userRole = localStorage.getItem("userRole");
    setIsAdmin(userRole === "System_Admin");
  }, []);

  useEffect(() => {
    const storedUserId = localStorage.getItem("userId");
    if (storedUserId) {
      // Fetch all users and groups
      axios
        .get("http://localhost:8085/api/user/users")
        .then((response) => {
          setAllUsers(response.data);
        })
        .catch((error) => {
          console.error("Error fetching users:", error);
          toast.warn("Error fetching users.");
        });

      axios
        .get("http://localhost:8085/api/group/groups")
        .then((response) => {
          setAllGroups(response.data);
        })
        .catch((error) => {
          console.error("Error fetching groups:", error);
          toast.warn("Error fetching groups.");
        });

      // Fetch pending friend requests
      friendService
        .getPendingRequests(storedUserId)
        .then((response) => {
          setPendingRequests(response.data.length);
        })
        .catch((error) => {
          console.error("Error fetching pending requests:", error);
          toast.warn("Error fetching pending requests.");
        });

      // Fetch approved join requests
      axios
        .get(`http://localhost:8085/api/join-requests/approved`, {
          params: { userId: storedUserId },
        })
        .then((response) => {
          setJoinRequestsApproved(response.data.length);
        })
        .catch((error) => {
          console.error("Error fetching approved join requests:", error);
          toast.warn("Error fetching approved join requests.");
        });

      // Fetch new category topic
      const latestTopicId = localStorage.getItem("latestTopicId");
      const lastViewedTopicId = localStorage.getItem("lastViewedTopicId");

      if (
        latestTopicId &&
        lastViewedTopicId &&
        latestTopicId !== lastViewedTopicId
      ) {
        setNewCategoryTopic(true);
      } else {
        axios
          .get("http://localhost:8085/api/topics/latest")
          .then((response) => {
            const fetchedTopicId = response.data.id;
            localStorage.setItem("latestTopicId", fetchedTopicId);

            if (fetchedTopicId !== lastViewedTopicId) {
              setNewCategoryTopic(true);
            }
          })
          .catch((error) => {
            console.error("Error fetching latest topic:", error);
            toast.warn("Error fetching latest topic.");
          });
      }
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
    localStorage.removeItem("latestTopicId");
    localStorage.removeItem("currentTopic");
    localStorage.removeItem("lastViewedTopicId");
    navigate("/login");
  };

  const notificationsPage = () => {
    navigate("/notifications");
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

  const hasNotifications =
    pendingRequests > 0 || joinRequestsApproved > 0 || newCategoryTopic;

  const handleSearchChange = (e) => {
    const query = e.target.value;
    setSearchQuery(query);

    if (query.length > 0) {
      let userResults = [];
      let groupResults = [];

      if (searchFilter === "all") {
        userResults = allUsers.filter(
          (user) =>
            user.firstName.toLowerCase().includes(query.toLowerCase()) ||
            user.lastName.toLowerCase().includes(query.toLowerCase())
        );
        groupResults = allGroups.filter((group) =>
          group.groupName.toLowerCase().includes(query.toLowerCase())
        );
      }

      if (searchFilter === "users") {
        userResults = allUsers.filter(
          (user) =>
            user.firstName.toLowerCase().includes(query.toLowerCase()) ||
            user.lastName.toLowerCase().includes(query.toLowerCase())
        );
      }

      if (searchFilter === "groups") {
        groupResults = allGroups.filter((group) =>
          group.groupName.toLowerCase().includes(query.toLowerCase())
        );
      }

      setSearchResults([
        ...userResults.map((user) => ({ ...user, type: "user" })),
        ...groupResults.map((group) => ({ ...group, type: "group" })),
      ]);
    } else {
      setSearchResults([]);
    }
  };

  const handleSearchResultClick = (result) => {
    // Navigate to the user or group profile page
    if (result.type === "user") {
      navigate(`/friendProfile/${result.email}`);
    } else if (result.type === "group") {
      navigate(`/groups/${result.id}`);
    }
    setSearchResults([]);
    setSearchQuery("");
  };

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const handleFilterChange = (filter) => {
    setSearchFilter(filter);
    setIsDropdownOpen(false);
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
              <input
                placeholder="Search DalBook"
                type="Search"
                value={searchQuery}
                onChange={handleSearchChange}
              />
              {searchResults.length > 0 && (
                <ul className="search-suggestions">
                  {searchResults.map((result) => (
                    <li
                      key={result.id}
                      onClick={() => handleSearchResultClick(result)}
                    >
                      {result.type === "user"
                        ? `${result.firstName} ${result.lastName}`
                        : result.groupName}
                    </li>
                  ))}
                </ul>
              )}
            </div>
            <div className="search-filters">
              <button className="btn" onClick={toggleDropdown}>Filter</button>
              {isDropdownOpen && (
                <ul className="dropdown-menu">
                  <li onClick={() => handleFilterChange("all")}>All</li>
                  <li onClick={() => handleFilterChange("users")}>Users</li>
                  <li onClick={() => handleFilterChange("groups")}>Groups</li>
                </ul>
              )}
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
              onClick={notificationsPage}
              style={{ position: "relative" }}
            >
              <IconBell stroke={2} size={30} color="#1877f2" />
              {hasNotifications && (
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
