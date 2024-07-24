import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "./Feed.css";
import {
  IconUsers,
  IconCalendarStar,
  IconFlag,
  IconChevronDown,
  IconUser,
  IconSearch,
  IconDots,
  IconPhoto,
  IconMoodSmile,
  IconThumbUp,
  IconMessageCircle,
  IconShare3,
  IconCircleCheckFilled,
  IconClockFilled,
  IconSquareMinusFilled,
  IconCircleXFilled,
  IconUsersGroup,
} from "@tabler/icons-react";
import friendService from "../../services/FriendService";

function Feed() {
  const [friends, setFriends] = useState([]);
  const navigate = useNavigate();
  const [userId, setUserId] = useState(null);
  const [user, setUser] = useState();

  const userProfile = () => {
    navigate("/profile");
  };

  const friendsPage = () => {
    navigate("/friendsList");
  };

  const groupsPage = () => {
    navigate("/");
  };

  const categoryOfDayPage = () => {
    navigate("/categoryOftheDay");
  };

  useEffect(() => {
    const storedUserId = localStorage.getItem("userId");
    const storedUserEmail = localStorage.getItem("userEmail");
    if (storedUserId && storedUserEmail) {
      setUserId(storedUserId);

      // Get Current User Information
      const fetchUser = async () => {
        try {
          const response = await axios.get(
            `http://localhost:8085/api/user/get/${storedUserEmail}`
          );
          setUser(response.data);
          console.log(response.data);
          console.log("User information retrieved successfully");
        } catch (error) {
          console.log("Error fetching user", error);
        }
      };
      fetchUser();

      // Get Current User Friends
      friendService
        .getFriends(storedUserId)
        .then((response) => {
          setFriends(response.data);
          console.log("Success fetching friends of user id:", userId);
        })
        .catch((error) => {
          console.error("Error fetching friends:", error);
          alert("An error occurred. Please try again!");
        });
    }
  }, [userId]);

  const getStatusIcon = (status) => {
    switch (status) {
      case "Available":
        return (
          <IconCircleCheckFilled color="green" style={{ paddingLeft: "5px" }} />
        );
      case "Away":
        return (
          <IconClockFilled color="orange" style={{ paddingLeft: "5px" }} />
        );
      case "Busy":
        return (
          <IconSquareMinusFilled color="red" style={{ paddingLeft: "5px" }} />
        );
      case "Offline":
        return (
          <IconCircleXFilled color="gray" style={{ paddingLeft: "5px" }} />
        );
      default:
        return null;
    }
  };

  return (
    <div className="main">
      <div className="left-side">
        <div
          className="profile"
          onClick={userProfile}
          style={{ cursor: "pointer" }}
        >
          <img
            src={
              user && user.profilePic
                ? `http://localhost:8085${user.profilePic}`
                : "/images/dalhousie-logo.png"
            }
            alt="profile-picture"
            style={{ padding: "1rem" }}
          />
          {user ? (
            <div className="user-name">
              {user.firstName + " " + user.lastName}
              {getStatusIcon(user.status)}
            </div>
          ) : (
            "Loading..."
          )}
        </div>
        <div className="panel" onClick={friendsPage}>
          <IconUsers stroke={2} />
          <div>Friends</div>
        </div>
        <div className="panel" onClick={groupsPage}>
          <IconUsersGroup stroke={2} />
          <div>Groups</div>
        </div>
        <div className="panel" onClick={categoryOfDayPage}>
          <IconCalendarStar stroke={2} />
          <div>Category of the Day</div>
        </div>
        <div className="panel">
          <IconFlag stroke={2} />
          <div>Pages</div>
        </div>
        <div className="panel">
          <IconChevronDown stroke={2} />
          <div>See More</div>
        </div>
        <div className="border"></div>
        <br />
        <a
          href="https://www.dal.ca/"
          target="_blank"
          rel="noopener noreferrer"
          style={{ textDecoration: "none", color: "black" }}
        >
          <div className="pages">
            <img
              src="/images/dalhousie-logo.png"
              alt="logo"
              style={{ padding: "1rem" }}
            />
            Dalhousie University
          </div>
        </a>
        <a
          href="https://daltigers.ca/sports/fball/index"
          target="_blank"
          rel="noopener noreferrer"
          style={{ textDecoration: "none", color: "black" }}
        >
          <div className="pages">
            <img
              src="/images/dalhousie-tigers-logo.png"
              alt="logo"
              style={{ padding: "1rem" }}
            />
            Dalhousie University Football
          </div>
        </a>
        <a
          href="https://dal.brightspace.com/d2l/home"
          target="_blank"
          rel="noopener noreferrer"
          style={{ textDecoration: "none", color: "black" }}
        >
          <div className="pages">
            <img
              src="/images/pulse.png"
              alt="logo"
              style={{ padding: "1rem" }}
            />
            Brightspace Pulse
          </div>
        </a>
        <div className="dropdown-panels">
          <IconChevronDown stroke={2} className="chevron" />
          <div>See More</div>
        </div>
      </div>
      <div className="timeline">
        {/* <div className="addStory">
          <div className="story">
            <img
              src="/images/avatar-2.jpeg"
              alt="logo"
              style={{ height: "50px", borderRadius: "50%" }}
            />
            <br />
            John Doe
          </div>
          <div className="story">
            <img
              src="/images/avatar-3.jpeg"
              alt="logo"
              style={{ height: "50px", borderRadius: "50%" }}
            />
            <br />
            John Doe
          </div>
          <div className="story">
            <img
              src="/images/avatar-3.jpeg"
              alt="logo"
              style={{ height: "50px", borderRadius: "50%" }}
            />
            <br />
            John Doe
          </div>
          <div className="story">
            <img
              src="/images/avatar-4.jpeg"
              alt="logo"
              style={{ height: "50px", borderRadius: "50%" }}
            />
            <br />
            John Doe
          </div>
        </div> */}
        <div className="create-post">
          <div className="text">
            <div className="user-post">
              <img
                src={
                  user && user.profilePic
                    ? `http://localhost:8085${user.profilePic}`
                    : "/images/dalhousie-logo.png"
                }
                alt="profile-picture"
                style={{ height: "50px", padding: "1rem" }}
              />
              <input type="Mind" placeholder="What's on your mind, John?" />
            </div>
            <div className="border-post"></div>
            <div className="post-icons">
              <div className="icon">
                <div className="post-icon">
                  <IconPhoto stroke={2} color="green" />
                  <div>Photo/Video</div>
                </div>
                <div className="post-icon">
                  <IconMoodSmile stroke={2} color="orange" />
                  <div>Feeling/Activity</div>
                </div>
              </div>
            </div>
          </div>
          <div className="posted">
            <div className="post">
              <div className="feed-profile-picture">
                <img
                  src="/images/avatar-1.jpeg"
                  alt=""
                  style={{ height: "50px" }}
                ></img>
                <div>John Doe</div>
                <div className="update">Updated his cover image.</div>
              </div>
              <div className="edit">
                <IconDots stroke={2} />
              </div>
            </div>
            <div className="caption">Feeling good today!</div>
            <br />
            <div className="media">
              <img
                src="/images/post.jpg"
                alt=""
                style={{ height: "auto", width: "100%" }}
              />
            </div>
            <div className="reactions">
              <div className="like">
                <IconThumbUp stroke={2} />
                Like
              </div>
              <div className="comment">
                <IconMessageCircle stroke={2} />
                Comment
              </div>
              <div className="share">
                <IconShare3 stroke={2} />
                Share
              </div>
            </div>
          </div>
          <div className="posted">
            <div className="post">
              <div className="feed-profile-picture">
                <img
                  src="/images/avatar-1.jpeg"
                  alt=""
                  style={{ height: "50px" }}
                ></img>
                <div>John Doe</div>
                <div className="update">Updated his cover image.</div>
              </div>
              <div className="edit">
                <IconDots stroke={2} />
              </div>
            </div>
            <div className="caption">Feeling good today!</div>
            <br />
            <div className="media">
              <img
                src="/images/post.jpg"
                alt=""
                style={{ height: "auto", width: "100%" }}
              />
            </div>
            <div className="reactions">
              <div className="like">
                <IconThumbUp stroke={2} />
                Like
              </div>
              <div className="comment">
                <IconMessageCircle stroke={2} />
                Comment
              </div>
              <div className="share">
                <IconShare3 stroke={2} />
                Share
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="right-side">
        <div className="contact">
          <div className="contacts">Friends</div>
          <div className="chat-icon">
            <div className="icons">
              <IconUser stroke={2} />
            </div>
            <div className="icons">
              <IconSearch stroke={2} />
            </div>
            <div className="icons">
              <IconDots stroke={2} />
            </div>
          </div>
        </div>
        <div className="concise">
          {friends.map((friend) => (
            <div key={friend.id} className="profiles name">
              <img
                src={
                  friend.profilePic
                    ? `http://localhost:8085${friend.profilePic}`
                    : "/images/dalhousie-logo.png"
                }
                alt="profile-picture"
                style={{ padding: "1rem" }}
              />
              {friend.firstName + " " + friend.lastName}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Feed;
